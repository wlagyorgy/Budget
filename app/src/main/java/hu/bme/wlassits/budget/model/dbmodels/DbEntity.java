package hu.bme.wlassits.budget.model.dbmodels;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

/**
 * Created by Adam Varga on 12/10/2017.
 */



public class DbEntity {
  private String id;
  private String fbId;
  private String type;
  private String description;
  private String value;
  private Date date;

    public DbEntity() {
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public DbEntity(String id, String fbId, String type, String description, String value, Date date) {
        this.id = id;
        this.fbId = fbId;
        this.type = type;
        this.description = description;
        this.value = value;
        this.date = date;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
