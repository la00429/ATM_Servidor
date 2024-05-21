package co.edu.uptc.presenter;

import co.edu.uptc.config.Config;
import co.edu.uptc.config.Message;
import co.edu.uptc.model.Course;
import co.edu.uptc.model.Student;
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
        sPrincipal.setStudents(loadData.readStudentAVLTreeFromJson(Message.PATH_USERS,Comparator.comparing(student -> student.getUser().getCode())));
        for (Student string: sPrincipal.getStudents()) {
            System.out.println(string.getUser().getCode());
        }
    }

    private void loadCoursesJSON() {
        sPrincipal.setCourses(loadData.readCourseAVLTreeFromJson(Message.PATH_COURSES, Comparator.comparing(Course::getName,Comparator.nullsFirst(String::compareTo))));
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
                    System.out.println(request.getTypeUser());
                    break;
                case "Show_name":
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
                    connetion.send(new Gson().toJson(new Responsive(Message.HELP)));
                    break;
                case "Us":
                    connetion.send(new Gson().toJson(new Responsive(Message.ABOUT_US)));
                    break;
            }
        } while (true);
    }


    /*try

        {
            connetion.send(Message.ABOUT_US);
        } catch(
                IOException ex)

        {
            throw new RuntimeException(ex);
        }

        showData(Message.ABOUT_US);*/
		/*String source = connetion.receive();
		if (source.equals("Login")) {
			verificationLogin();
		}

		if (source.equals("Forgot")) {
			forgotPassword();
		}

		if (source.equals("Next")) {
			createUserData();
		}

		if (source.equals("Record")) {
			loadDataCourse();
		}

		if (source.equals("Accept")) {
			updatePanelChangePasswaord();
		}

		if (source.equals("Create")) {
			changeToCreateUser();
		}

		if (source.equals("Logout")) {
			logOutSystem();
		}

		if (source.equals("Help")) {
            try {
                connetion.send(Message.HELP);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            showData(Message.HELP);

		}

		if(source.equals("Us"))

		 */

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

/* *//**
 * Navega entre el panel del curso y vuelve a login.
 *//*
    public void logOutSystem() {
        view.accessLogin();
    }

    *//**
 * Navegaci�n entre el panel login y el de creaci�n de usuario.
 *//*
    public void changeToCreateUser() {
        view.accessCreate();
    }

    *//**
 * Actualiza el cambio de contrase�a.
 *//*
    public void updatePanelChangePasswaord() {
        String codeUser = view.getFrameApp().getChangePassword().getUserInput();
        String passwordUserNew = view.getFrameApp().getChangePassword().getPasswordInput();
        if (!codeUser.isEmpty() && !passwordUserNew.isEmpty()) {
            verificationUser(codeUser);
        } else {
            view.getFrameApp().showMessageInfo(Message.ERROR_NULL);
        }
    }*/

/**
 * Verification user.
 *
 * @param codeUser the code user
 *//*
    private void verificationUser(String codeUser) {
        if (sPrincipal.getUsers().containsKey(codeUser)) {
            updateStatePasword(codeUser);
        } else {
            view.getFrameApp().showMessageInfo(Message.ERROR_NO_FOUND);
        }
    }

    *//**
 * Update state pasword.
 *
 * @param codeUser the code user
 *//*
    private void updateStatePasword(String codeUser) {
        changeDataUser(codeUser);
        view.accessLoginChange();
        view.getFrameApp().getChangePassword().cleanPanel();
    }

    *//**
 * Change data user.
 *
 * @param codeUser the code user
 *//*
    private void changeDataUser(String codeUser) {
        sPrincipal.changePassword(codeUser, view.getFrameApp().getChangePassword().getPasswordInput());
        loadData.writeUsersJSON(sPrincipal.getUsers());
    }

    *//**
 * Cargar los datos correspondientes al curso.
 *//*
    public void loadDataCourse() {
        getDataCourse();
        getDataUser();
        cleanDataPanel();
    }

    *//**
 * Gets the data course.
 *
 * @return the data course
 *//*
    private void getDataCourse() {
        String courseSelect = view.getFrameApp().selectCourse();
        String nameUser = view.getFrameApp().getCreateUser().getName();
        loadCourse(courseSelect, nameUser);
    }

    *//**
 * Load course.
 *
 * @param courseSelect the course select
 * @param nameUser     the name user
 *//*
    private void loadCourse(String courseSelect, String nameUser) {
        view.getFrameApp().setCourse(sPrincipal.selectCourse(courseSelect));
        view.getFrameApp().setNameUser(nameUser);
        view.accessCourseCreate();
    }

    *//**
 * Gets the data user.
 *
 * @return the data user
 *//*
    private void getDataUser() {
        String name = view.getFrameApp().getCreateUser().getName();
        String code = view.getFrameApp().getCreateUser().getCode();
        String gender = view.getFrameApp().getCreateUser().getSelectedGender();
        String password = view.getFrameApp().getCreateUser().getPasswordInput();
        String styleLearning = view.getFrameApp().getFormStyleLearning().getSelectStyle();
        loadDataUser(name, code, gender, password, styleLearning);
    }

    *//**
 * Load data user.
 *
 * @param name          the name
 * @param code          the code
 * @param gender        the gender
 * @param password      the password
 * @param styleLearning the style learning
 *//*
    private void loadDataUser(String name, String code, String gender, String password, String styleLearning) {
        sPrincipal.addUser(code, name, gender, password, styleLearning);
        loadData.writeUsersJSON(sPrincipal.getUsers());
    }

    *//**
 * Clean data panel.
 *//*
    private void cleanDataPanel() {
        view.getFrameApp().getCreateUser().cleanPanel();
        view.getFrameApp().getFormStyleLearning().cleanPanel();
    }

    *//**
 * m�todo para navegar en la creaci�n de usuario.
 *//*
    public void createUserData() {
        String name = view.getFrameApp().getCreateUser().getName();
        String code = view.getFrameApp().getCreateUser().getCode();
        String gender = view.getFrameApp().getCreateUser().getSelectedGender();
        String password = view.getFrameApp().getCreateUser().getPasswordInput();
        createUserMessage(name, code, gender, password);
    }

    *//**
 * Creates the user message.
 *
 * @param name     the name
 * @param code     the code
 * @param gender   the gender
 * @param password the password
 *//*
    private void createUserMessage(String name, String code, String gender, String password) {
        if (name.isEmpty() || code.isEmpty() || gender.isEmpty() || password.isEmpty()) {
            showData(Message.ERROR_NULL);
        } else {
            if (sPrincipal.getUsers().containsKey(code)) {
                showData(Message.ERROR_TWIN);
            } else {
                createUserNext();
            }
        }
    }

    *//**
 * Creates the user next.
 *//*
    private void createUserNext() {
        view.accessForm();
    }

    *//**
 * Realiza la acci�n para olvido de contrase�a.
 *//*
    public void forgotPassword() {
        view.accessChange();
    }


    *//**
 * Login acess.
 *
 * @param codeUser the code user
 *//*
    private void loginAcess(String codeUser) {
        view.getFrameApp().stateLoginUser(false);
        showName(codeUser);
        selectCourse(codeUser);

        view.accessCourseCreate();
        view.getFrameApp().getLoginUser().cleanPanel();
    }

    *//**
 * Login message.
 *
 * @param codeUser     the code user
 * @param passwordUser the password user
 *//*
    private void loginMessage(String codeUser, String passwordUser) {
        if (codeUser.isEmpty() || passwordUser.isEmpty()) {
            showData(Message.ERROR_NULL);
        } else {
            showData(Message.ERROR_NO_FOUND);
        }
    }

    *//**
 * Select course.
 *
 * @param codeUser the code user
 *//*
    private void selectCourse(String codeUser) {
        view.getFrameApp().getCourse().getWebCourse().loadPage(sPrincipal.selectCourse(sPrincipal.showUser(codeUser).getStyleLearning()));
    }

    *//**
 * Show name.
 *
 * @param codeUser the code user
 *//*
    private void showName(String codeUser) {
        view.getFrameApp().getCourse().setNameUser(sPrincipal.showUser(codeUser).getName());
    }

    *//**
 * M�todo para mostrar mensajes dentro de los JDialogs.
 *
 * @param message el mensaje qu quiero que se muestre.
 *//*
    public void showData(String message) {
        view.showData(message);
    }*/
}
