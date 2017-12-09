package hu.bme.wlassits.budget.model;



public class User {
    private String first_name;

    public User(String name) {
        this.first_name = name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
}
