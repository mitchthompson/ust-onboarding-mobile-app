package com.example.practicumapp.models;

import java.util.HashMap;

public class Task {
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;

public class Task extends ExpandableGroup<TaskDescription> {

    private String id, name, viewers;
    private HashMap descriptions;

    /**
     * Constructor
     *
     * @param id           Unique id of the task
     * @param name         Display name of the task
     * @param descriptions Descriptions of the task
     * @param viewers      Employee Instructions for the task
     */
    public Task(String id, String name, HashMap descriptions, String viewers) {

        public Task(String id, String name, String description, String employeeInstructions,
                String managerInstructions, ArrayList<TaskDescription> childDescription) {
        super(name, childDescription);
        this.id = id;
        this.name = name;
        this.descriptions = descriptions;
        this.viewers = viewers;
    }

    /**
     * @return Unique id of the task
     */
    public String getId() {
        return id;
    }

    /**
     * @return Display name of the task
     */
    public String getName() {
        return name;
    }

    /**
     * @return Descriptions of the task
     */
    public HashMap getDescriptions() {
        return descriptions;
    }

    /**
     * @return Employee Instructions for the task
     */
    public String getViewers() {
        return viewers;
    }

}