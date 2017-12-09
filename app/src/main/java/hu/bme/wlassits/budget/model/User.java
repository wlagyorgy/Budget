package hu.bme.wlassits.budget.model;

/**
 * Created by Adam Varga on 12/9/2017.
 */

class User {
    private String name;

    User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
