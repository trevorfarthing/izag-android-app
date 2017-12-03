package com.example.tfarthing.izagradio;

/**
 * Created by tfarthing on 12/2/17.
 */

public class Show {
    private String title;
    private String description;
    private String imageURL;

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
}
