package co.edu.uptc.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import co.edu.uptc.structures.AVLTree;


public class SystemPrincipal  {

	private AVLTree<Course> courses;
	private AVLTree<Student> students;

	public SystemPrincipal() {
		this.students = new AVLTree<>(Comparator.comparing(student->student.getUser().getCode()));
		this.courses = new AVLTree<>(Comparator.comparing(Course::getName));

	}

	public synchronized boolean loginStudent(String code, String password) {
		boolean verification = false;
		Iterable<Student> students = this.students.inOrder();
		for (Student student : students) {
			verification = verificationLogin(student, code, password);
		}
		return verification;
	}

	private synchronized boolean verificationLogin(Student student, String code, String password) {
		boolean verification = false;
		int count = 0;
		if (count ==0 && student.getUser().isAvailable()) {
			if (student.getUser().getCode().equals(code) && student.getUser().getPassword().equals(password)) {
				verification = true;
				count++;
			}
		}
		return verification;
	}

	public synchronized boolean loginAdmin(String code, String password) {
		boolean verification = false;
		int count = 0;
		Iterable<Student> students = this.students.inOrder();
		for (Student student : students) {
			verification = verificationLogin(student, code, password);
		}
		return verification;
	}


	public synchronized boolean addStudent(Student student) {
		boolean verification = false;
		if (students.searchData(student) == null) {
			students.insert(student);
			verification = true;
		}
		return true;
	}

	public synchronized boolean changePassword(String codeUser, String passwordNew) {
		boolean verification = false;
		Iterable<Student> students = this.students.inOrder();
		while (students.iterator().hasNext()) {
			Student student = students.iterator().next();
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
		while (courses.iterator().hasNext()) {
			Course course = courses.iterator().next();
			if (course.getName().equals(courseName)) {
				courseFound = course.getInformation();
			}
		}
		return courseFound;
	}



	public synchronized Student showUser(String codeUser) {
		Student studentFound = new Student();
		Iterable<Student> students = this.students.inOrder();
		while (students.iterator().hasNext()) {
			Student student = students.iterator().next();
			if (student.getUser().getCode().equals(codeUser)) {
				studentFound = student;
			}
		}
		return studentFound;
	}

	public synchronized boolean verificationUser(String code) {
		boolean verification = false;
		Iterable<Student> students = this.students.inOrder();
		while (students.iterator().hasNext()) {
			Student student = students.iterator().next();
			if (student.getUser().getCode().equals(code)) {
					verification = true;
			}
		}
		return verification;
	}

	public synchronized boolean blockUser(String codeUser) {
		boolean verification = false;
		Iterable<Student> students = this.students.inOrder();
		while (students.iterator().hasNext()) {
			Student student = students.iterator().next();
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
		while (students.iterator().hasNext()) {
			Student student = students.iterator().next();
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
		while (courses.iterator().hasNext()) {
			Course course = courses.iterator().next();
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
		while (courses.iterator().hasNext()) {
			Course course = courses.iterator().next();
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

	public synchronized List<Student> getStudents() {
		return students.inOrder();
	}

	public synchronized void setCourses(AVLTree<Course> courses) {
		this.courses = courses;
	}

	public synchronized void setStudents(AVLTree<Student> students) {
		this.students = students;
	}
}
