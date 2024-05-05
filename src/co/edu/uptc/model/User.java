package co.edu.uptc.model;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * Clase que representa un usuario en el sistema. Implementa Serializable para
 * permitir la serializaci�n de objetos.
 */
public class User implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The code. */
	private String code; // C�digo del usuario
	
	/** The name. */
	private String name; // Nombre del usuario
	
	/** The gender. */
	private String gender; // G�nero del usuario
	
	/** The password. */
	private String password; // Contrase�a del usuario
	
	/** The style learning. */
	private String styleLearning; // Estilo de aprendizaje del usuario

	/**
	 * Constructor vac�o de la clase User.
	 */
	public User() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor de la clase User que recibe par�metros para inicializar los
	 * atributos.
	 *
	 * @param code          C�digo del usuario.
	 * @param name          Nombre del usuario.
	 * @param gender        G�nero del usuario.
	 * @param password      Contrase�a del usuario.
	 * @param styleLearning Estilo de aprendizaje del usuario.
	 */
	public User(String code, String name, String gender, String password, String styleLearning) {
		super();
		this.code = code;
		this.name = name;
		this.gender = gender;
		this.password = password;
		this.styleLearning = styleLearning;
	}

	// M�todos para acceder y modificar los atributos de la clase User

	/**
	 * Obtiene el c�digo del usuario.
	 *
	 * @return El c�digo del usuario.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Establece el c�digo del usuario.
	 *
	 * @param code El c�digo del usuario a establecer.
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Obtiene el nombre del usuario.
	 *
	 * @return El nombre del usuario.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Establece el nombre del usuario.
	 *
	 * @param name El nombre del usuario a establecer.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Obtiene el g�nero del usuario.
	 *
	 * @return El g�nero del usuario.
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Establece el g�nero del usuario.
	 *
	 * @param gender El g�nero del usuario a establecer.
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Obtiene la contrase�a del usuario.
	 *
	 * @return La contrase�a del usuario.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Establece la contrase�a del usuario.
	 *
	 * @param password La contrase�a del usuario a establecer.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Obtiene el estilo de aprendizaje del usuario.
	 *
	 * @return El estilo de aprendizaje del usuario.
	 */
	public String getStyleLearning() {
		return styleLearning;
	}

	/**
	 * Establece el estilo de aprendizaje del usuario.
	 *
	 * @param styleLearning El estilo de aprendizaje del usuario a establecer.
	 */
	public void setStyleLearning(String styleLearning) {
		this.styleLearning = styleLearning;
	}
}
