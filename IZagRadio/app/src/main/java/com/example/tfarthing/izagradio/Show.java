package com.example.tfarthing.izagradio;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by tfarthing on 12/2/17.
 */

public class Show implements Serializable {
    private String title;
    private String description;
    private String imageURL;
    private String soundcloudURL;

    public Show() {
        this.title = "Default title";
        this.description = "Default description";
        this.imageURL = "";
    }

    public Show(String title, String description) {
        this.title = title;
        this.description = description;
        imageURL = "";
    }

    public Show(String title, String description, String imageURL) {
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSoundcloudURL() {
        return soundcloudURL;
    }

    public void setSoundcloudURL(String soundcloudURL) {
        this.soundcloudURL = soundcloudURL;
    }
}
