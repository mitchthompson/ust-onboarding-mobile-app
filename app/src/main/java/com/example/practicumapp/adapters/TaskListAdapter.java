package com.example.practicumapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.practicumapp.R;
import com.example.practicumapp.TaskDescriptionListItemViewHolder;
import com.example.practicumapp.TaskListActivity;
import com.example.practicumapp.TaskListItemViewHolder;
import com.example.practicumapp.models.TaskDescriptionListItem;
import com.example.practicumapp.models.TaskListItem;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jsayler on 1/14/18.
 *
 * Modified by lschwarz on 2/11/18
 * TaskListAdapter creates the ViewHolders both for the TaskListItems and also the TaskDescriptionListItems.
 * This is how the expandable RecyclerView gets created.
 */
public class TaskListAdapter extends ExpandableRecyclerViewAdapter<TaskListItemViewHolder, TaskDescriptionListItemViewHolder> {

    Context context;
    private ArrayList<TaskListItem> taskListItems = new ArrayList<>();


    public TaskListAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
        for(int i = 0; i < groups.size(); i++) {
            taskListItems.add((TaskListItem) groups.get(i));
        }
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
    public void onBindGroupViewHolder(final TaskListItemViewHolder holder, int flatPosition, final ExpandableGroup group) {
        final TaskListItem myItem = taskListItems.get(flatPosition);
        holder.setTaskName(group);

        //in some cases, it will prevent unwanted situations
        holder.checkBox.setOnCheckedChangeListener(null);

        //if true, checkbox will be selected, else unselected
        holder.checkBox.setChecked(myItem.isChecked());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                myItem.setChecked(b);

                if(myItem.isChecked()) {
                    TaskListActivity.ProgressBarIncrement(1);
                }
                else {
                    TaskListActivity.ProgressBarIncrement(-1);
                }
            }
        });






    }
}
