package com.example.practicumapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.practicumapp.R;
import com.example.practicumapp.TaskDescriptionListItemViewHolder;
import com.example.practicumapp.TaskListItemViewHolder;
import com.example.practicumapp.models.TaskDescriptionListItem;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by jsayler on 1/14/18.
 *
 * JS - this is the task activity's view adapter. it takes data piped into it and displays it in the
 * recycler view
 *
 * any changes here will probably need to be made when creating the expandable line items as
 * indicated on the wireframe. this will require modifications to onBindViewHolder and the
 * TaskListViewHolder
 */

public class TaskListAdapter extends ExpandableRecyclerViewAdapter<TaskListItemViewHolder, TaskDescriptionListItemViewHolder> {

    public TaskListAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public TaskListItemViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_items,parent,false);
        return new TaskListItemViewHolder(view);
    }

    @Override
    public TaskDescriptionListItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_description_items, parent, false);
        return new TaskDescriptionListItemViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(TaskDescriptionListItemViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final TaskDescriptionListItem taskDescriptionListItem = (TaskDescriptionListItem) (group).getItems().get(childIndex);
        holder.setChildTextView(taskDescriptionListItem.getDescription());
    }

    @Override
    public void onBindGroupViewHolder(TaskListItemViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setTaskName(group);
    }
}
