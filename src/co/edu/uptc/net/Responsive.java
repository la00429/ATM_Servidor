package co.edu.uptc.net;

import java.util.List;

public class Responsive {
    //datos la respuesta del servidor para hacer algo.
    private String message;
    private List<String> courseNames;
    private List<String> codeStudents;
    private boolean verification;


    public Responsive(String message) {
        this.message = message;
    }

    public Responsive(List<String> list, int option) {
        switch (option) {
            case 1:
                this.courseNames = list;
                break;
            case 2:
                this.codeStudents = list;
                break;
        }
    }

    public Responsive(boolean verification) {
        this.verification = verification;
    }

    public Responsive(String message, boolean verification) {
        this.message = message;
        this.verification = verification;
    }

    public String getMessage() {
        return message;
    }

    public boolean getVerification() {
        return verification;
    }

    public List<String> getCodeStudents() {
        return codeStudents;
    }

    public List<String> getCourseNames() {
        return courseNames;
    }
}
