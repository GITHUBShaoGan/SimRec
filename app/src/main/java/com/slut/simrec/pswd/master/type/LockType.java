package com.slut.simrec.pswd.master.type;

/**
 * Created by 七月在线科技 on 2016/12/11.
 */

public class LockType {

    private String title;

    private String description;

    private int imageID;

    public LockType(String title, String description, int imageID) {
        this.title = title;
        this.description = description;
        this.imageID = imageID;
    }

    @Override
    public String toString() {
        return "LockType{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageID=" + imageID +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
