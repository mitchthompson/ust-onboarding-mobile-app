package com.example.practicumapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.practicumapp.R;
import com.example.practicumapp.TaskListActivity;

import java.util.ArrayList;

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

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder> {

    private Context taskContext;
    private View taskView;
    private TaskListViewHolder taskViewHolder;
    private LayoutInflater inflater;
    
    //TODO: change this to an array of tasks from a workflow?
    private ArrayList<String> taskData = new ArrayList();

    public TaskListAdapter(Context newContext, ArrayList<String> newData) {
        this.taskContext = newContext;
        inflater = LayoutInflater.from(taskContext);
        this.taskData = newData;
    }

    @Override
    public TaskListViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        taskView = LayoutInflater.from(taskContext).inflate(R.layout.task_items,parent,false);
        taskViewHolder = new TaskListViewHolder(taskView);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(TaskListViewHolder holder, int position) {
        holder.taskTextView.setText(taskData.get(position).toString());

        //TODO: We need to store the checked/unchecked status in our model,
        // and then call it here in a method, like this maybe:
        // holder.taskCheckBox.setChecked(isChecked())

        holder.taskCheckBox.setChecked(true);
    }

    @Override
    public int getItemCount() {
        return taskData.size();
    }

    public class TaskListViewHolder extends RecyclerView.ViewHolder {
        public CheckBox taskCheckBox;
        public TextView taskTextView;
        public TaskListViewHolder(View v) {
            super(v);
            taskCheckBox = (CheckBox) v.findViewById(R.id.task_checkbox);
            taskTextView = (TextView) v.findViewById(R.id.task_list_item);

            taskCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                /*
                 * Handles listening for changes to the checkboxes, if a checkbox changes
                 * TODO: Set the isChecked value of the task in the model so that the state is tracked
                 * Otherwise the recycler view will just reload it as checked when you scroll.
                 */
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(taskCheckBox.isChecked()) {
                        TaskListActivity.ProgressBarIncrement(1);
                    }
                    else {
                        TaskListActivity.ProgressBarIncrement(-1);
                    }
                }
            });


        }

    }
}
