package com.example.practicumapp.models;

import java.util.ArrayList;

public class Workflow {

    private String id, name, description;
    private ArrayList<Task> tasks;

    public Workflow (String id, String name, String description, ArrayList<Task> tasks) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tasks = tasks;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
