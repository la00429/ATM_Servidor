package co.edu.uptc.presenter;

import co.edu.uptc.config.Config;
import co.edu.uptc.config.Message;
import co.edu.uptc.model.Course;
import co.edu.uptc.model.SystemPrincipal;
import co.edu.uptc.net.Connection;
import co.edu.uptc.net.Request;
import co.edu.uptc.net.Responsive;
import co.edu.uptc.persistence.LoadData;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.Socket;
import java.util.Comparator;

public class ClientThread extends Thread {
    private Connection connetion;
    private SystemPrincipal sPrincipal;
    private LoadData loadData;

    public ClientThread(Socket socket, SystemPrincipal sPrincipal, LoadData loadData) throws IOException {
        this.connetion = new Connection(socket);
        this.sPrincipal = sPrincipal;
        this.loadData = loadData;
    }

    @Override
    public void run() {
        try {
            super.run();
            loadData();
            menuPrincipal();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadData() {
        loadProperties();
        loadStudentsJSON();
        loadCoursesJSON();
    }

    private void loadProperties() {
        Config config = new Config();
        config.loadMessages();
    }

    private void loadStudentsJSON() {
        sPrincipal.setStudents(loadData.readStudentAVLTreeFromJson(Message.PATH_USERS, Comparator.comparing(student -> student.getUser().getCode())));
    }

    private void loadCoursesJSON() {
        sPrincipal.setCourses(loadData.readCourseAVLTreeFromJson(Message.PATH_COURSES, Comparator.comparing(Course::getName)));
    }

    public void menuPrincipal() throws IOException {

        establishConnection();
        Request request;
        do {
            request = new Gson().fromJson(connetion.receive(), Request.class);
            switch (request.getOption()) {
                case "Load_Styles":
                    connetion.send(new Gson().toJson(new Responsive(loadData.readTxt(Message.PATH_STYLES), 1)));
                    break;
                case "Gender":
                    connetion.send(new Gson().toJson(new Responsive(loadData.readTxt(Message.PATH_GENDER), 2)));
                    break;
                case "Login":
                    verificationLogin(request.getCodeUser(), request.getPasswordUser(), request.getTypeUser());
                    break;
                case "Show_Name":
                    connetion.send(new Gson().toJson(new Responsive(sPrincipal.showUser(request.getCodeUser()).getName())));
                    break;
                case "Show_Course_CourseName":
                    connetion.send(new Gson().toJson(new Responsive(sPrincipal.searchCourse(request.getCourseName()))));
                    break;
                case "Show_Course_CodeUser":
                    connetion.send(new Gson().toJson(new Responsive(sPrincipal.searchCourse(sPrincipal.showUser(request.getCodeUser()).getStyleLearning()))));
                    break;
                case "Add_User":
                    if (sPrincipal.addStudent(request.getStudent())) {
                        connetion.send(new Gson().toJson(new Responsive(Message.MESSAGE_SUCCESS, sPrincipal.addStudent(request.getStudent()))));
                        loadData.writeStudentAVLTreeToJson(sPrincipal.getStudents(), Message.PATH_USERS);
                    } else {
                        connetion.send(new Gson().toJson(new Responsive(Message.MESSAGE_NO_SUCCESS)));
                    }
                    break;
                case "Exist_User":
                    connetion.send(new Gson().toJson(new Responsive(Message.ERROR_NO_FOUND, sPrincipal.verificationUser(request.getCodeUser()))));
                    break;
                case "Change_Password":
                    if (sPrincipal.changePassword(request.getCodeUser(), request.getPasswordUser())) {
                        connetion.send(new Gson().toJson(new Responsive(Message.MESSAGE_CHANGES)));
                        loadData.writeStudentAVLTreeToJson(sPrincipal.getStudents(), Message.PATH_USERS);
                    } else {
                        connetion.send(new Gson().toJson(new Responsive(Message.MESSAGE_NO_SUCCESS)));
                    }
                    break;
                case "Error_Null":
                    connetion.send(new Gson().toJson(new Responsive(Message.ERROR_NULL)));
                    break;
                case "Error_No_Found":
                    connetion.send(new Gson().toJson(new Responsive(Message.ERROR_NO_FOUND)));
                    break;
                case "Error_Twin":
                    connetion.send(new Gson().toJson(new Responsive(Message.ERROR_TWIN, sPrincipal.verificationUser(request.getCodeUser()))));
                    break;
                case "Help":
                    System.out.println(request.getOption());
                    connetion.send(new Gson().toJson(new Responsive(Message.HELP)));
                    break;
                case "Us":
                    System.out.println(request.getOption());
                    connetion.send(new Gson().toJson(new Responsive(Message.ABOUT_US)));
                    break;
                default:
                    System.err.println("Conexión cerrada");
            }
        } while (true);
    }

    private void establishConnection() throws IOException {
        connetion.connect();
        Gson gson = new Gson();
        connetion.send(gson.toJson(new Responsive("Connection established")));
        Responsive message = gson.fromJson(connetion.receive(), Responsive.class);
        System.err.println(message.getMessage());
    }

    private void verificationLogin(String codeUser, String passwordUser, String typeUser) throws IOException {
        switch (typeUser) {
            case "student":
                connetion.send(new Gson().toJson(new Responsive(sPrincipal.loginStudent(codeUser, passwordUser))));
                break;
            case "ADMIN":
                connetion.send(new Gson().toJson(new Responsive(sPrincipal.loginAdmin(codeUser, passwordUser))));
                break;
        }
    }
}
