package co.edu.uptc.model;

import co.edu.uptc.structures.AVLTree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class SystemPrincipal  {

	private AVLTree<Course> courses;
	private AVLTree<Student> students;

	public SystemPrincipal() {
		this.students = new AVLTree<>(Comparator.comparing(student->student.getUser().getCode()));
		this.courses = new AVLTree<>(Comparator.comparing(Course::getName));

	}

	public synchronized boolean login(String code, String password) {
		boolean verification = false;
		Iterable<Student> students = this.students.inOrder();
		for (Student student : students) {
			if (!verification) {
				verification = isStudentSystem(student, code, password);
			}
		}
		return verification;

	}

	private synchronized boolean isStudentSystem(Student student, String code, String password) {
		boolean verification = false;
		if (student.getUser().isAvailable()) {
			if (student.getUser().getCode().equals(code) && student.getUser().getPassword().equals(password)) {
				verification = true;
			}
		}
		return verification;
	}

	public synchronized boolean addStudent(Student student) {
		boolean verification = false;
		if (students.searchData(student) == null) {
			students.insert(student);
			verification = true;

		}
		return verification;
	}

	public synchronized boolean changePassword(String codeUser, String passwordNew) {
		boolean verification = false;
		Iterable<Student> students = this.students.inOrder();
		for (Student student : students) {
			if (student.getUser().getCode().equals(codeUser)) {
				student.getUser().setPassword(passwordNew);
				verification = true;
			}
		}
		return verification;
	}


	public synchronized String searchCourse(String courseName){
		String courseFound = new String();
		Iterable<Course> courses = this.courses.inOrder();
		for (Course course : courses) {
			if (course.getName().equals(courseName)) {
				if (course.getAvailable()) {
					courseFound = course.getInformation();
				}
			}
		}
		return courseFound;
	}

	public synchronized Student showUser(String codeUser) {
		Student studentFound = new Student();
		Iterable<Student> students = this.students.inOrder();
		for (Student student : students) {
			if (student.getUser().getCode().equals(codeUser)) {
				studentFound = student;
			}
		}
		return studentFound;
	}

	public synchronized boolean verificationUser(String code) {
		boolean verification = false;
		Iterable<Student> students = this.students.inOrder();
		for (Student student : students) {
			if (student.getUser().getCode().equals(code)) {
				verification = true;
			}
		}
		return verification;
	}

	public synchronized boolean blockUser(String codeUser) {
		boolean verification = false;
		Iterable<Student> students = this.students.inOrder();
		for (Student student : students) {
			if (student.getUser().getCode().equals(codeUser)) {
				student.getUser().setAvailable(false);
				verification = true;
			}
		}
		return verification;
	}

	public synchronized boolean unblockUser(String codeUser) {
		boolean verification = false;
		Iterable<Student> students = this.students.inOrder();
		for (Student student : students) {
			if (student.getUser().getCode().equals(codeUser)) {
				student.getUser().setAvailable(true);
				verification = true;
			}
		}
		return verification;
	}

	public synchronized boolean blockCourse(String courseName) {
		boolean verification = false;
		Iterable<Course> courses = this.courses.inOrder();
		for (Course course : courses) {
			if (course.getName().equals(courseName)) {
				course.setAvailable(false);
				verification = true;
			}
		}
		return verification;
	}

	public synchronized boolean unblockCourse(String courseName) {
		boolean verification = false;
		Iterable<Course> courses = this.courses.inOrder();
		for (Course course : courses) {
			if (course.getName().equals(courseName)) {
				course.setAvailable(true);
				verification = true;
			}
		}
		return verification;
	}

	public synchronized List<Course> getCourses() {
		return courses.inOrder();
	}

	public synchronized List<String> coursesNames() {
		List<String> names = new ArrayList<>();
		Iterable<Course> courses = this.courses.inOrder();
		for (Course course : courses) {
			names.add(course.getName());
		}
		return names;
	}

	public synchronized List<Student> getStudents() {
		return students.inOrder();
	}

	public synchronized List<String> studentsCodes() {
		List<String> codes = new ArrayList<>();
		Iterable<Student> students = this.students.inOrder();
		for (Student student : students) {
			if (!student.getUser().getCode().equals("0")) {
				codes.add(student.getUser().getCode());
			}
		}
		return codes;
	}

	public synchronized void setCourses(AVLTree<Course> courses) {
		this.courses = courses;
	}

	public synchronized void setStudents(AVLTree<Student> students) {
		this.students = students;
	}

	public int countStudentsInCourse(String courseName) {
		int countStudent = 0;
		Iterable<Student> students = this.students.inOrder();
		for (Student student : students) {
			if (student.getStyleLearning().equals(courseName)) {
				countStudent++;

			}
		}
		return countStudent;
	}
}
