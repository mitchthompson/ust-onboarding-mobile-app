package com.example.practicumapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.practicumapp.R;
import com.example.practicumapp.TaskListActivity;
import com.example.practicumapp.models.Task;
import com.example.practicumapp.models.TaskDescription;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.ArrayList;
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

public class TaskListAdapter extends ExpandableRecyclerViewAdapter<TaskListAdapter.TaskListViewHolder, TaskListAdapter.TaskDescriptionViewHolder> {

    private Context taskContext;
    private View taskView;
    private TaskListViewHolder taskViewHolder;
    private LayoutInflater inflater;
    //TODO: change this to an array of tasks from a workflow?
    private ArrayList<String> taskData = new ArrayList();

    public TaskListAdapter(Context newContext, List<Task> newData) {
        super(newData);
        this.taskContext = newContext;
        inflater = LayoutInflater.from(taskContext);
        //this.taskData = newData;
    }

    @Override
    public TaskListViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        taskView = LayoutInflater.from(taskContext).inflate(R.layout.task_items,parent,false);
        taskViewHolder = new TaskListViewHolder(taskView);
        return taskViewHolder;
    }

    /*
    @Override
    public void onBindViewHolder(TaskListViewHolder holder, int position) {
        //set task name
        holder.taskTextView.setText(taskData.get(position).toString());

        //set and hide task description
        //holder.taskDescriptionView.setText(taskData.get(position).toString());
        //holder.

        //TODO: We need to store the checked/unchecked status in our model,
        // and then call it here in a method, like this maybe:
        // holder.taskCheckBox.setChecked(isChecked())

        holder.taskCheckBox.setChecked(true);
    }
    */

    @Override
    public int getItemCount() {
        return taskData.size();
    }

    @Override
    public TaskListViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.task_items, parent, false);
        return new TaskListViewHolder(view);
    }

    @Override
    public TaskDescriptionViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.task_description_items, parent, false);
        return new TaskDescriptionViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(TaskDescriptionViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final TaskDescription taskDescription = (TaskDescription) (group).getItems().get(childIndex);
        holder.setTaskDescriptionText(taskDescription.getDescription());

    }

    @Override
    public void onBindGroupViewHolder(TaskListViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setTaskTextView(group);
    }

    public class TaskListViewHolder extends GroupViewHolder {
        public CheckBox taskCheckBox;
        public TextView taskTextView;
        public TextView taskDescriptionView;
        public TaskListViewHolder(View v) {
            super(v);
            taskCheckBox = (CheckBox) v.findViewById(R.id.task_checkbox);
            taskTextView = (TextView) v.findViewById(R.id.task_list_item);

            //Set and hide taskDescription
            taskDescriptionView = (TextView) v.findViewById(R.id.task_description);
            taskDescriptionView.setVisibility(View.INVISIBLE);

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

            taskTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (taskDescriptionView.getVisibility() == View.VISIBLE) {
                        taskDescriptionView.setVisibility(View.INVISIBLE);
                    }
                    else {
                        taskDescriptionView.setVisibility(View.VISIBLE);
                    }
                }
            });

        }

        public void setTaskTextView(ExpandableGroup group) {
            taskTextView.setText(group.getTitle());
        }

    }

    public class TaskDescriptionViewHolder extends ChildViewHolder {
        private String taskDescriptionText;

        public TaskDescriptionViewHolder(View itemView) {
            super(itemView);
            taskDescriptionText = itemView.findViewById(R.id.task_description).toString();
        }

        public void onBind(TaskDescription taskDescription) {
            taskDescription.setDescription(taskDescriptionText.toString());
        }

        public void setTaskDescriptionText(String taskDescriptionTextIn) {
            this.taskDescriptionText =  taskDescriptionTextIn;
        }
    }
}
