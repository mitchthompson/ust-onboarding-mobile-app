package com.example.practicumapp.models;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by lucasschwarz on 2/10/18.
 */

public class TaskListItem extends ExpandableGroup<TaskDescriptionListItem> {

    private String taskName;

    public TaskListItem(String taskName, List<TaskDescriptionListItem> description) {
        super(taskName, description);
    }

    public void setTaskName(String nameIn) {
        this.taskName = nameIn;
    }
}
