package co.edu.uptc.model;


public class Student {
    private  String name;
    private String gender;
    private String styleLearning;
    private User user;

    public Student(String name, String gender, String styleLearning, User user) {
        this.name = name;
        this.gender = gender;
        this.styleLearning = styleLearning;
        this.user = user;
    }

    public Student() {
    }

    public String getName() {
        return name;
    }

    public String getStyleLearning() {
        return styleLearning;
    }

    public User getUser() {
        return user;
    }
}

