package com.example.tfarthing.izagradio;

/**
 * Created by jtwheadon on 12/7/17.
 */

public class Staff {
    private String name;
    private String title;

    public Staff(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
