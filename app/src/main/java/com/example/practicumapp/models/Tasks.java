package com.example.practicumapp.models;

/**
 * Created by jsayler on 1/13/18.
 */

public class Tasks {

    private String title;
    private String description;

    public Tasks() {}

    public void setTaskTitle(String value) {
        title = value;
    }

    public void setDescription(String value) {
        description = value;
    }

    public String getTaskTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
