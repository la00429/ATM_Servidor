package co.edu.uptc.presenter;

// TODO: Auto-generated Javadoc
/**
 * Esta interfaz define los contratos (interfaces) para las vistas,
 * presentadores y modelos.
 */
public interface Contracts {
	/**
	 * Interfaz que define los métodos que debe implementar una vista.
	 */
	public interface IView {
		/**
		 * Muestra un mensaje en la vista.
		 *
		 * @param message El mensaje a mostrar.
		 */
		public void showData(String message);

		/**
		 * Establece el nombre del curso en la vista.
		 *
		 * @param course El nombre del curso a establecer.
		 */
		public void setCourse(String course);

		/**
		 * Accede al inicio de sesión.
		 */
		public void accessLogin();

		/**
		 * Accede al sesión desde el cambio de contraseña.
		 */
		public void accessLoginChange();

		/**
		 * Accede al curso.
		 */
		public void accessCourse();

		/**
		 * Accede al panel de creación de usuario.
		 */
		public void accessCreate();

		/**
		 * Accede al curso desde la ruta de paneles para crear un usuario.
		 */
		public void accessCourseCreate();

		/**
		 * Accede al cambio de contraseña.
		 */
		public void accessChange();

		/**
		 * Accede al formulario de estilos de aprendizaje.
		 */
		public void accessForm();
	}

	/**
	 * Interfaz que define los métodos que debe implementar un presentador.
	 */
	public interface IPresenter {

		/**
		 * Realiza la verificación del inicio de sesión.
		 */
		public void verificationLogin();

		/**
		 * Realiza la acción para olvido de contraseña.
		 */
		public void forgotPassword();

		/**
		 * método para navegar en la creación de usuario.
		 */
		public void createUserData();

		/**
		 * Cargar los datos correspondientes al curso.
		 */
		public void loadDataCourse();

		/**
		 * Actualiza el cambio de contraseña.
		 */
		public void updatePanelChangePasswaord();

		/**
		 * Navegación entre el panel login y el de creación de usuario.
		 */
		public void changeToCreateUser();
		
		/**
		 * Navega entre el panel del curso y vuelve a login.
		 */
		public void logOutSystem();

		/**
		 * Método para mostrar mensajes dentro de los JDialogs.
		 *
		 * @param message el mensaje qu quiero que se muestre.
		 */
		public void showData(String message);

	}

	/**
	 * Muestra información de un usuario basado en su código.
	 *
	 * @return El objeto que representa al usuario.
	 */
	public interface IModel {

		  /**
         * Muestra información de un usuario basado en su código.
         *
         * @param codeUser El código del usuario.
         * @return El objeto que representa al usuario.
         */
		public Object showUser(String codeUser);

		/**
		 * Agrega un nuevo usuario.
		 *
		 * @param code          El código del usuario.
		 * @param name          El nombre del usuario.
		 * @param gender        El género del usuario.
		 * @param password      La contraseña del usuario.
		 * @param styleLearning El estilo de aprendizaje del usuario.
		 */
		public void addUser(String code, String name, String gender, String password, String styleLearning);

		/**
		 * Agrega un nuevo curso.
		 *
		 * @param infoCourse La información del curso a agregar.
		 */
		public void addCourse(String infoCourse);

		/**
		 * Verifica la existencia de un usuario basado en su código y contraseña.
		 *
		 * @param code     El código del usuario.
		 * @param password La contraseña del usuario.
		 * @return Verdadero si el usuario existe, falso en caso contrario.
		 */
		public Boolean verificationUser(String code, String password);

		/**
		 * Cambia la contraseña de un usuario.
		 *
		 * @param codeUser    El código del usuario.
		 * @param passwordNew La nueva contraseña del usuario.
		 */
		public void changePassword(String codeUser, String passwordNew);

		/**
		 * Selecciona un curso basado en su nombre.
		 *
		 * @param nameCourse El nombre del curso.
		 * @return El curso seleccionado.
		 */
		public String selectCourse(String nameCourse);

	}

}
