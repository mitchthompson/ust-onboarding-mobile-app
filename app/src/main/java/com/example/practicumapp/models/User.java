package com.example.practicumapp.models;

public class User {

    private String id, userName, firstName, lastName, email, phone, type;

    public User(String id, String userName, String firstName, String lastName, String email, String phone, String type) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.type = type;
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

    public String getUserName() {
        return userName;
    }

}
