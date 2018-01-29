package com.example.practicumapp.models;

import java.util.ArrayList;

public class User {

    private String id, firstName, lastName, email, phone, type, manager, workflow;
    private ArrayList employees, tasks;

    public User(String id, String firstName, String lastName, String email, String phone, String type, String manager, ArrayList employees, String workflow, ArrayList tasks) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.type = type;
        this.manager = manager;
        this.employees = employees;
        this.workflow = workflow;
        this.tasks = tasks;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getType() {
        return type;
    }

    public String getManager() {
        return manager;
    }

    public ArrayList getEmployees() {
        return employees;
    }

    public String getWorkflow() {
        return workflow;
    }

    public ArrayList getTasks() {
        return tasks;
    }
}
