package com.example.practicumapp.models;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private String id, firstName, lastName, email, phone, type, startDate, workflow;
    private ArrayList tasks;
    private HashMap employees;

    /**
     * Constructor
     * @param id User id
     * @param firstName User's first name
     * @param lastName User's last name
     * @param email User's email
     * @param phone User's phone number
     * @param type User's type can be one of the following - "employee" or "manager"
     * @param startDate User's date of employment
     * @param employees HashMap of IDs and names for the manager's assigned employees
     * @param workflow Workflow id assigned to the user
     * @param tasks An array of task IDs user has completed
     */
    public User(String id, String firstName, String lastName, String email, String phone, String type, String startDate, HashMap employees, String workflow, ArrayList tasks) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.type = type;
        this.startDate = startDate;
        this.employees = employees;
        this.workflow = workflow;
        this.tasks = tasks;
    }

    /**
     * Constructor to use when creating a new user object to send to the API
     * @param firstName User's first name
     * @param lastName User's last name
     * @param email User's email
     * @param phone User's phone number
     * @param type User's type can be one of the following - "employee" or "manager"
     * @param startDate User's date of employment
     * @param workflow Workflow id assigned to the user
     */
    public User(String firstName, String lastName, String email, String phone, String type, String startDate, String workflow) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.type = type;
        this.startDate = startDate;
        this.workflow = workflow;
    }

    /**
     * Set User's id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return User's id
     */
    public String getId() {
        return id;
    }

    /**
     * @return User's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return User's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return User's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return User's phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @return User's type
     */
    public String getType() {
        return type;
    }

    /**
     * @return User's date of employment
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @return ArrayList of IDs for the manager's assigned employees
     */
    public HashMap getEmployees() {
        return employees;
    }

    /**
     * @return User's workflow id
     */
    public String getWorkflow() {
        return workflow;
    }

    /**
     * @return an array of completed task IDs
     */
    public ArrayList getTasks() {
        return tasks;
    }
}