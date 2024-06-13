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
import java.util.List;

public class ClientThread extends Thread {
    private Connection connection;
    private SystemPrincipal sPrincipal;
    private LoadData loadData;

    public ClientThread(Socket socket, SystemPrincipal sPrincipal, LoadData loadData) throws IOException {
        this.connection = new Connection(socket);
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
            System.err.println("Connection closed: " + connection.showIP());
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
            request = new Gson().fromJson(connection.receive(), Request.class);
            switch (request.getOption()) {
                case "Load_Courses":
                    loadListData(sPrincipal.coursesNames(), 1);
                    break;
                case "Load_Users":
                    loadListData(sPrincipal.studentsCodes(), 2);
                    break;
                case "Login":
                    verificationLogin(request.getCodeUser(), request.getPasswordUser());
                    break;
                case "Show_Name":
                    showName(request);
                    break;
                case "Show_Course_CourseName":
                    showCourseCourseName(request);
                    break;
                case "Show_Course_CodeUser":
                    showCourseCodeUser(request);
                    break;
                case "Show_Count_Course":
                    showCountCourse(request);
                    break;
                case "Add_User":
                    addUser(request);
                    break;
                case "Exist_User":
                    exitUser(request);
                    break;
                case "Change_Password":
                    changePassword(request);
                    break;
                case "Block_User":
                    blockUser(request);
                    break;
                case "Unblock_User":
                    unBlockUser(request);
                    break;
                case "Block_Course":
                    blockCourse(request);
                    break;
                case "Unblock_Course":
                    unBlockCourse(request);
                    break;
                case "Disconnect":
                    closeConnection();
            }
        } while (true);
    }

    private void loadListData(List<String> list, int option) throws IOException {
        connection.send(new Gson().toJson(new Responsive(list, option)));
    }

    private void verificationLogin(String codeUser, String passwordUser) throws IOException {
        connection.send(new Gson().toJson(new Responsive(sPrincipal.login(codeUser, passwordUser))));
    }

    private void showName(Request request) throws IOException {
        connection.send(new Gson().toJson(new Responsive(sPrincipal.showUser(request.getCodeUser()).getName())));
    }

    private void showCourseCourseName(Request request) throws IOException {
        connection.send(new Gson().toJson(new Responsive(sPrincipal.searchCourse(request.getCourseName()))));

    }

    private void showCourseCodeUser(Request request) throws IOException {
        connection.send(new Gson().toJson(new Responsive(sPrincipal.searchCourse(sPrincipal.showUser(request.getCodeUser()).getStyleLearning()))));
    }

    private void showCountCourse(Request request)throws IOException {
        connection.send(new Gson().toJson(new Responsive(String.valueOf(sPrincipal.countStudentsInCourse(request.getCourseName())))));
    }

    private void addUser(Request request) throws IOException {
        boolean verificationAddUser = sPrincipal.addStudent(request.getStudent());
        if (verificationAddUser) {
            connection.send(new Gson().toJson(new Responsive(true)));
            loadData.writeStudentAVLTreeToJson(sPrincipal.getStudents(), Message.PATH_USERS);
        } else {
            connection.send(new Gson().toJson(new Responsive(false)));
        }
    }


    private void exitUser(Request request) throws IOException {
        connection.send(new Gson().toJson(new Responsive(sPrincipal.verificationUser(request.getCodeUser()))));
    }

    private void changePassword(Request request) throws IOException {
        boolean verificationChange = sPrincipal.changePassword(request.getCodeUser(), request.getPasswordUser());
        if (verificationChange) {
            connection.send(new Gson().toJson(new Responsive(true)));
            loadData.writeStudentAVLTreeToJson(sPrincipal.getStudents(), Message.PATH_USERS);
        } else {
            connection.send(new Gson().toJson(new Responsive(false)));
        }
    }

    private void blockUser(Request request) throws IOException {
        boolean verificationBlockUser = sPrincipal.blockUser(request.getCodeUser());
        if (verificationBlockUser) {
            connection.send(new Gson().toJson(new Responsive(true)));
            loadData.writeStudentAVLTreeToJson(sPrincipal.getStudents(), Message.PATH_USERS);
        } else {
            connection.send(new Gson().toJson(new Responsive(false)));
        }
    }

    private void unBlockUser(Request request) throws IOException {
        boolean verificationUnblockUser = sPrincipal.unblockUser(request.getCodeUser());
        if (verificationUnblockUser) {
            connection.send(new Gson().toJson(new Responsive(true)));
            loadData.writeStudentAVLTreeToJson(sPrincipal.getStudents(), Message.PATH_USERS);
        } else {
            connection.send(new Gson().toJson(new Responsive(false)));
        }
    }

    private void blockCourse(Request request) throws IOException {
        boolean verificationBlock = sPrincipal.blockCourse(request.getCourseName());
        if (verificationBlock) {
            connection.send(new Gson().toJson(new Responsive(true)));
            loadData.writeCourseAVLTreeToJson(sPrincipal.getCourses(), Message.PATH_COURSES);
        } else {
            connection.send(new Gson().toJson(new Responsive(false)));
        }
    }

    private void unBlockCourse(Request request) throws IOException {
        boolean verificationUnblock = sPrincipal.unblockCourse(request.getCourseName());
        if (verificationUnblock) {
            connection.send(new Gson().toJson(new Responsive(true)));
            loadData.writeCourseAVLTreeToJson(sPrincipal.getCourses(), Message.PATH_COURSES);
        } else {
            connection.send(new Gson().toJson(new Responsive(false)));
        }
    }

    private void establishConnection() throws IOException {
        connection.connect();
        connection.send(new Gson().toJson(new Responsive("Connection established with Server")));
        System.err.println(new Gson().fromJson(connection.receive(), Responsive.class).getMessage()+": " + connection.showIP() );
    }

    private void closeConnection() throws IOException {
        connection.send(new Gson().toJson(new Responsive("Closing Connection")));
        connection.disconnect();
    }
}
