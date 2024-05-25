package co.edu.uptc.persistence;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import co.edu.uptc.model.Student;
import co.edu.uptc.model.Course;
import co.edu.uptc.structures.AVLTree;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LoadData {

	public void writeStudentAVLTreeToJson(List<Student> tree, String filename) {
		createResources(filename);
		try (FileWriter writer = new FileWriter(filename)) {
			Gson gson = new Gson();
			gson.toJson(tree, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public AVLTree<Student> readStudentAVLTreeFromJson(String filename, Comparator<Student> comparator) {
		createResources(filename);
		AVLTree<Student> tree = new AVLTree<>(comparator);
		try (FileReader reader = new FileReader(filename)) {
			Gson gson = new Gson();
			Type studentListType = new TypeToken<List<Student>>() {}.getType();
			List<Student> students = gson.fromJson(reader, studentListType);
			for (Student student : students) {
				tree.insert(student);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tree;
	}

	public void writeCourseAVLTreeToJson(List<Course> tree, String filename) {
		createResources(filename);
		try (FileWriter writer = new FileWriter(filename)) {
			Gson gson = new Gson();
			gson.toJson(tree, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public AVLTree<Course> readCourseAVLTreeFromJson(String filename, Comparator<Course> comparator) {
		createResources(filename);

		AVLTree<Course> tree = new AVLTree<>(comparator);
		try (FileReader reader = new FileReader(filename)) {
			Gson gson = new Gson();
			Type courseListType = new TypeToken<List<Course>>() {}.getType();
			List<Course> courses = gson.fromJson(reader, courseListType);
			for (Course course : courses) {
				tree.insert(course);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tree;
	}


	private void createResources(String path) {
		File file = new File(path);
		File parentDir = file.getParentFile();
		if (parentDir != null && !parentDir.exists()) {
			parentDir.mkdirs();
		}
	}

}
