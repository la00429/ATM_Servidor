package co.edu.uptc.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import co.edu.uptc.config.Config;
import co.edu.uptc.config.Message;
import co.edu.uptc.model.SystemPrincipal;
import co.edu.uptc.model.User;
import co.edu.uptc.persistence.LoadData;
import co.edu.uptc.view.View;

// TODO: Auto-generated Javadoc
/**
 * Clase Presenter que act�a como controlador en el patr�n MVC. Se encarga de manejar la l�gica de negocio
 * y la interacci�n entre la vista y el modelo.
 */
public class Presenter extends MouseAdapter implements ActionListener, Contracts.IPresenter {

	/** The view. */
//	private View view;
	
	/** The s principal. */
	private SystemPrincipal sPrincipal;
	
	/** The load data. */
	private LoadData loadData;

	/**
	 * Instantiates a new presenter.
	 */
	public Presenter() {
		this.loadData = new LoadData();
		this.sPrincipal = new SystemPrincipal();
		this.view = new View(this, this);
	}

	/**
	 * Load data.
	 */
	public void loadData() {
		loadProperties();
		loadJSON();
		loadSystem();

	}

	/**
	 * Load properties.
	 */
	private void loadProperties() {
		Config config = new Config();
		config.loadMessages();
		this.view.getFrameApp().revalidate();
		this.view.getFrameApp().repaint();
	}

	/**
	 * Load JSON.
	 */
	private void loadJSON() {
		sPrincipal.setUsers(loadData.readUsersJSON());
	}

	/**
	 * Load system.
	 */
	private void loadSystem() {
		loadUsers();
		loadGender();
		loadStyles();
	}

	/**
	 * Load styles.
	 */
	private void loadStyles() {
		view.getFrameApp().loadComboStyles(loadData.readTxt(Message.PATH_STYLES));
	}

	/**
	 * Load gender.
	 */
	private void loadGender() {
		view.getFrameApp().loadComboGender(loadData.readTxt(Message.PATH_GENDER));
	}

	/**
	 * Load users.
	 */
	private void loadUsers() {
		ArrayList<String> sites = loadData.readTxt(Message.PATH_SITES);
		for (String infoCourse : sites) {
			sPrincipal.addCourse(infoCourse);
		}
	}

	/**
	 * Action performed.
	 *
	 * @param e the e
	 */
	
	public void actionPerformed(ActionEvent e) {
		String source = e.getActionCommand();
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
			showData(Message.HELP);

		}

		if (source.equals("Us")) {
			showData(Message.ABOUT_US);
		}
	}

	/**
	 * Navega entre el panel del curso y vuelve a login.
	 */
	public void logOutSystem() {
		view.accessLogin();
	}

	/**
	 * Navegaci�n entre el panel login y el de creaci�n de usuario.
	 */
	public void changeToCreateUser() {
		view.accessCreate();
	}

	/**
	 * Actualiza el cambio de contrase�a.
	 */
	public void updatePanelChangePasswaord() {
		String codeUser = view.getFrameApp().getChangePassword().getUserInput();
		String passwordUserNew = view.getFrameApp().getChangePassword().getPasswordInput();
		if (!codeUser.isEmpty() && !passwordUserNew.isEmpty()) {
			verificationUser(codeUser);
		} else {
			view.getFrameApp().showMessageInfo(Message.ERROR_NULL);
		}
	}

	/**
	 * Verification user.
	 *
	 * @param codeUser the code user
	 */
	private void verificationUser(String codeUser) {
		if (sPrincipal.getUsers().containsKey(codeUser)) {
			updateStatePasword(codeUser);
		} else {
			view.getFrameApp().showMessageInfo(Message.ERROR_NO_FOUND);
		}
	}

	/**
	 * Update state pasword.
	 *
	 * @param codeUser the code user
	 */
	private void updateStatePasword(String codeUser) {
		changeDataUser(codeUser);
		view.accessLoginChange();
		view.getFrameApp().getChangePassword().cleanPanel();
	}

	/**
	 * Change data user.
	 *
	 * @param codeUser the code user
	 */
	private void changeDataUser(String codeUser) {
		sPrincipal.changePassword(codeUser, view.getFrameApp().getChangePassword().getPasswordInput());
		loadData.writeUsersJSON(sPrincipal.getUsers());
	}

	/**
	 * Cargar los datos correspondientes al curso.
	 */
	public void loadDataCourse() {
		getDataCourse();
		getDataUser();
		cleanDataPanel();
	}

	/**
	 * Gets the data course.
	 *
	 * @return the data course
	 */
	private void getDataCourse() {
		String courseSelect = view.getFrameApp().selectCourse();
		String nameUser = view.getFrameApp().getCreateUser().getName();
		loadCourse(courseSelect, nameUser);
	}

	/**
	 * Load course.
	 *
	 * @param courseSelect the course select
	 * @param nameUser the name user
	 */
	private void loadCourse(String courseSelect, String nameUser) {
		view.getFrameApp().setCourse(sPrincipal.selectCourse(courseSelect));
		view.getFrameApp().setNameUser(nameUser);
		view.accessCourseCreate();
	}

	/**
	 * Gets the data user.
	 *
	 * @return the data user
	 */
	private void getDataUser() {
		String name = view.getFrameApp().getCreateUser().getName();
		String code = view.getFrameApp().getCreateUser().getCode();
		String gender = view.getFrameApp().getCreateUser().getSelectedGender();
		String password = view.getFrameApp().getCreateUser().getPasswordInput();
		String styleLearning = view.getFrameApp().getFormStyleLearning().getSelectStyle();
		loadDataUser(name, code, gender, password, styleLearning);
	}

	/**
	 * Load data user.
	 *
	 * @param name the name
	 * @param code the code
	 * @param gender the gender
	 * @param password the password
	 * @param styleLearning the style learning
	 */
	private void loadDataUser(String name, String code, String gender, String password, String styleLearning) {
		sPrincipal.addUser(code, name, gender, password, styleLearning);
		loadData.writeUsersJSON(sPrincipal.getUsers());
	}

	/**
	 * Clean data panel.
	 */
	private void cleanDataPanel() {
		view.getFrameApp().getCreateUser().cleanPanel();
		view.getFrameApp().getFormStyleLearning().cleanPanel();
	}

	/**
	 * m�todo para navegar en la creaci�n de usuario.
	 */
	public void createUserData() {
		String name = view.getFrameApp().getCreateUser().getName();
		String code = view.getFrameApp().getCreateUser().getCode();
		String gender = view.getFrameApp().getCreateUser().getSelectedGender();
		String password = view.getFrameApp().getCreateUser().getPasswordInput();
		createUserMessage(name, code, gender, password);
	}

	/**
	 * Creates the user message.
	 *
	 * @param name the name
	 * @param code the code
	 * @param gender the gender
	 * @param password the password
	 */
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

	/**
	 * Creates the user next.
	 */
	private void createUserNext() {
		view.accessForm();
	}

	/**
	 * Realiza la acci�n para olvido de contrase�a.
	 */
	public void forgotPassword() {
		view.accessChange();
	}

	/**
	 * Realiza la verificaci�n del inicio de sesi�n.
	 */
	public void verificationLogin() {
		String codeUser = view.getFrameApp().getLoginUser().getUserInput();
		String passwordUser = view.getFrameApp().getLoginUser().getPasswordInput();
		boolean searchUsers = sPrincipal.verificationUser(codeUser, passwordUser);
		if (searchUsers == true) {
			loginAcess(codeUser);
		} else {
			loginMessage(codeUser, passwordUser);
		}
	}

	/**
	 * Login acess.
	 *
	 * @param codeUser the code user
	 */
	private void loginAcess(String codeUser) {
		view.getFrameApp().stateLoginUser(false);
		showName(codeUser);
		selectCourse(codeUser);
		
		view.accessCourseCreate();
		view.getFrameApp().getLoginUser().cleanPanel();
	}

	/**
	 * Login message.
	 *
	 * @param codeUser the code user
	 * @param passwordUser the password user
	 */
	private void loginMessage(String codeUser, String passwordUser) {
		if (codeUser.isEmpty() || passwordUser.isEmpty()) {
			showData(Message.ERROR_NULL);
		} else {
			showData(Message.ERROR_NO_FOUND);
		}
	}

	/**
	 * Select course.
	 *
	 * @param codeUser the code user
	 */
	private void selectCourse(String codeUser) {
		view.getFrameApp().getCourse().getWebCourse().loadPage(sPrincipal.selectCourse(sPrincipal.showUser(codeUser).getStyleLearning()));
	}

	/**
	 * Show name.
	 *
	 * @param codeUser the code user
	 */
	private void showName(String codeUser) {
		view.getFrameApp().getCourse().setNameUser(sPrincipal.showUser(codeUser).getName());
	}

	/**
	 * M�todo para mostrar mensajes dentro de los JDialogs.
	 *
	 * @param message el mensaje qu quiero que se muestre.
	 */
	public void showData(String message) {
		view.showData(message);
	}

}
