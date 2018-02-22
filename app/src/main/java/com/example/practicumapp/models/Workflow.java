package com.example.practicumapp.models;

import java.util.ArrayList;

public class Workflow {

    private String id, name, description;
    private ArrayList<Task> tasks;

    /**
     * Constructor
     * @param id Unique id of the workflow
     * @param name Display name of the workflow
     * @param description Description of the workflow
     * @param tasks ArrayList of task objects for the workflow
     */
    public Workflow (String id, String name, String description, ArrayList<Task> tasks) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tasks = tasks;
    }

    /**
     * Set an unique id for the workflow
     */
    public void setID(String id) {
        this.id = id;
    }

    /**
     * @return Unique id of the workflow
     */
    public String getId() {
        return id;
    }

    /**
     * @return Display name of the workflow
     */
    public String getName() {
        return name;
    }

    /**
     * @return Description of the workflow
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return ArrayList of task objects for the workflow
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }
}