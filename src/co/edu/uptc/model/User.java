package co.edu.uptc.model;
public class User{
	private String code;
	private String password;
	private boolean available;
	private String type;

	public User(String code, String password) {
		super();
		this.code = code;
		this.password = password;
		this.available = true;
		this.type = "student";
	}
	public String getCode() {
		return code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getType() {
		return type;
	}
}
