package hu.bme.wlassits.budget.model;

import android.graphics.drawable.Drawable;

import java.util.Date;


public class Income {
    private String id;
    private String description;
    private int value;
    private Date date;
    private Drawable img;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Income() {
    }

    public Income(String id, String description, int value, Date date, Drawable img, String type) {
        this.id = id;
        this.description = description;
        this.value = value;
        this.date = date;
        this.img = img;
        this.type = type;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }
}
