package co.edu.uptc.model;

import java.util.ArrayList;
import java.util.HashMap;

import co.edu.uptc.presenter.Contracts;

// TODO: Auto-generated Javadoc
/**
 * Clase que implementa la interfaz IModel y gestiona la lógica del sistema
 * principal. Esta clase contiene métodos para manipular usuarios y cursos en el
 * sistema.
 */
public class SystemPrincipal implements Contracts.IModel {

	/** The courses. */
	private ArrayList<Course> courses; // Lista de cursos en el sistema
	
	/** The users. */
	private HashMap<String, User> users; // Mapa de usuarios en el sistema

	/**
	 * Constructor de la clase SystemPrincipal. Inicializa las listas de cursos y
	 * usuarios al crear una instancia de esta clase.
	 */
	public SystemPrincipal() {
		courses = new ArrayList<Course>();
		users = new HashMap<String, User>();
	}

	/**
	 * Muestra información de un usuario basado en su código.
	 *
	 * @param codeUser El código del usuario.
	 * @return El objeto que representa al usuario.
	 */
	public User showUser(String codeUser) {
		return getUsers().get(codeUser);

	}

	/**
	 * Agrega un nuevo curso.
	 *
	 * @param infoCourse La información del curso a agregar.
	 */
	public void addCourse(String infoCourse) {
		String[] info = infoCourse.split("_");
		Course course = new Course(info[0], info[1]);
		courses.add(course);
	}

	/**
	 * Agrega un nuevo usuario.
	 *
	 * @param code          El código del usuario.
	 * @param name          El nombre del usuario.
	 * @param gender        El género del usuario.
	 * @param password      La contraseña del usuario.
	 * @param styleLearning El estilo de aprendizaje del usuario.
	 */
	public void addUser(String code, String name, String gender, String password, String styleLearning) {
		User user = new User(code, name, gender, password, styleLearning);
		users.put(code, user);
	}

	/**
	 * Verifica la existencia de un usuario basado en su código y contraseña.
	 *
	 * @param code     El código del usuario.
	 * @param password La contraseña del usuario.
	 * @return Verdadero si el usuario existe, falso en caso contrario.
	 */
	public Boolean verificationUser(String code, String password) {
		Boolean verification = false;
		for (String codeUser : users.keySet()) {
			if (codeUser.equals(code)) {
				if (users.get(code).getPassword().equals(password)) {
					verification = true;
				}
			}
		}
		return verification;
	}

	/**
	 * Selecciona un curso basado en su nombre.
	 *
	 * @param nameCourse El nombre del curso.
	 * @return El curso seleccionado.
	 */
	public String selectCourse(String nameCourse) {
		String courseSelect = new String();
		for (Course course : courses) {
			if (course.getName().equals(nameCourse)) {
				courseSelect = course.getInfo();
			}
		}
		return courseSelect;
	}

	/**
	 * Cambia la contraseña de un usuario.
	 *
	 * @param codeUser    El código del usuario.
	 * @param passwordNew La nueva contraseña del usuario.
	 */
	public void changePassword(String codeUser, String passwordNew) {
		getUsers().get(codeUser).setPassword(passwordNew);
	}

	/**
	 * Devuelve el arrayList de los cursos.
	 *
	 * @return arrayList
	 */
	public ArrayList<Course> getCourses() {
		return courses;
	}

	/**
	 * Devuelve el HashMap de Usuarios.
	 *
	 * @return un hashMap de Usuarios
	 */
	public HashMap<String, User> getUsers() {
		return users;
	}

	/**
	 * Asisgna un hashMap a users. Se usa en el presenter para persisitir los
	 * usuarios a la clase princiopal de modelo. 
	 *
	 * @param users the users
	 */
	public void setUsers(HashMap<String, User> users) {
		this.users = users;
	}

}
