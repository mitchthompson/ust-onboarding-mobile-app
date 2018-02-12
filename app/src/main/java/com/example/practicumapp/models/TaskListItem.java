package com.example.practicumapp.models;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * General Object to describe a Task for the TaskListActivity ExpandableRecyclerView
 * String taskName is the name of the Task
 */

public class TaskListItem extends ExpandableGroup<TaskDescriptionListItem> {

    private String taskName;

    /**
     * Each TaskListItem has an arrayList of descriptions that go along with it.
     * These are displayed in the UI of the Expandable RecyclerView
     * @param taskName
     * @param description
     */
    public TaskListItem(String taskName, List<TaskDescriptionListItem> description) {
        super(taskName, description);
    }

    /**
     * General setter for the taskName
     * @param nameIn
     */
    public void setTaskName(String nameIn) {
        this.taskName = nameIn;
    }
}
