package co.edu.uptc.model;

public class Course {

	private String name;
	private String information;
	private boolean available;


	public Course(String name, String information) {
		this.name = name;
		this.information = information;
		this.available = true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}
}