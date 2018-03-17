package com.example.practicumapp.models;

import java.util.HashMap;

/**
 * Task Model Class
 * @author Suraj Upreti
 */

public class Task {

    private String name, viewers;
    private Integer id;
    private HashMap descriptions;

    /**
     * Constructor
     * @param id           Unique id of the task
     * @param name         Display name of the task
     * @param descriptions Descriptions of the task
     * @param viewers      Employee Instructions for the task
     */
    public Task(Integer id, String name, HashMap descriptions, String viewers) {
        this.id = id;
        this.name = name;
        this.descriptions = descriptions;
        this.viewers = viewers;
    }

    /**
     * @return Unique id of the task
     */
    public Integer getId() {
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