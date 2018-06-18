package com.example.practicumapp.models;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * General Object to describe a Task for the TaskListActivity ExpandableRecyclerView
 * String taskName is the name of the Task
 */

public class TaskListItem extends ExpandableGroup<TaskDescriptionListItem> {

    private String taskName;
    private boolean isChecked;
    private Integer taskID;

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

    /**
     * @return
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * @param checked
     */
    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    /**
     * @return
     */
    public boolean isChecked() {
        return isChecked;
    }

    /**
     * @return
     */
    public Integer getTaskID() {
        return taskID;
    }

    /**
     * @param taskID
     */
    public void setTaskID(Integer taskID) {
        this.taskID = taskID;
    }
}
