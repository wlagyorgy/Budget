package hu.bme.wlassits.budget.model;



public class User {
    private String first_name;
    private String facebookIdentifier;

    public User() {
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getFacebookIdentifier() {
        return facebookIdentifier;
    }

    public void setFacebookIdentifier(String facebookIdentifier) {
        this.facebookIdentifier = facebookIdentifier;
    }
}
