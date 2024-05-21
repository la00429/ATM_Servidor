package co.edu.uptc.net;

import java.util.List;

public class Responsive {
    //datos la respuesta del servidor para hacer algo.
    private String message;
    private List<String> stylesLearning;
    private List<String> genders;
    private boolean verification;


    public Responsive(String message) {
        this.message = message;
    }

    public Responsive(List<String> list, int option) {
        switch (option) {
            case 1:
                this.stylesLearning = list;
                break;
            case 2:
                this.genders = list;
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

    public List<String> getStylesLearning() {
        return stylesLearning;
    }

    public List<String> getGenders() {
        return genders;
    }

    public boolean getVerification() {
        return verification;
    }
}
