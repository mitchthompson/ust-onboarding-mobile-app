package com.example.practicumapp.models;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;

public class Task extends ExpandableGroup<TaskDescription> {

    private String id, name, description, employeeInstructions, managerInstructions;

    public Task(String id, String name, String description, String employeeInstructions,
                String managerInstructions, ArrayList<TaskDescription> childDescription) {
        super(name, childDescription);
        this.id = id;
        this.name = name;
        this.description = description;
        this.employeeInstructions = employeeInstructions;
        this.managerInstructions = managerInstructions;
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

    public String getEmployeeInstructions() {
        return employeeInstructions;
    }

    public  String getManagerInstructions() {
        return managerInstructions;
    }
}
