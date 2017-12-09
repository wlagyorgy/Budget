package hu.bme.wlassits.budget.model;

import android.graphics.drawable.Drawable;

import java.util.Date;


public class Outlay {
    private int id;
    private String description;
    private int value;
    private Date date;
    private Drawable img;
    private OutlayType type;

    public OutlayType getType() {
        return type;
    }

    public void setType(OutlayType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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


    @Override
    public String toString() {
        return "Outlay{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", value=" + value +
                ", date=" + date +
                ", img=" + img +
                ", type=" + type +
                '}';
    }
}
