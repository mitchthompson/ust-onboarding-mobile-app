package com.example.practicumapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.practicumapp.R;
import com.example.practicumapp.TaskDescriptionListItemViewHolder;
import com.example.practicumapp.TaskListActivity;
import com.example.practicumapp.TaskListItemViewHolder;
import com.example.practicumapp.VolleyParser;
import com.example.practicumapp.models.TaskDescriptionListItem;
import com.example.practicumapp.models.TaskListItem;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jsayler on 1/14/18.
 *
 * Modified by lschwarz on 2/11/18
 * TaskListAdapter creates the ViewHolders both for the TaskListItems and also the TaskDescriptionListItems.
 * This is how the expandable RecyclerView gets created.
 */
public class TaskListAdapter extends ExpandableRecyclerViewAdapter<TaskListItemViewHolder, TaskDescriptionListItemViewHolder> {

    // declare local variables
    private static final String TAG = TaskListAdapter.class.getName(); // Constant for logging data

    Context context;
    private ArrayList<TaskListItem> taskListItems = new ArrayList<>();
    private HashMap<String, TaskListItem> taskCheckedMap;
    private VolleyParser adapterVolleyParser;
    private String accessToken;


    public TaskListAdapter(List<? extends ExpandableGroup> groups, String accessToken) {
        super(groups);

        // assign the HashMap
        taskCheckedMap = new HashMap<>();
        for(int i = 0; i < groups.size(); i++) {
            //taskListItems.add((TaskListItem) groups.get(i));
            taskCheckedMap.put(((TaskListItem) groups.get(i)).getTitle(), (TaskListItem) groups.get(i));
            Log.d(TAG, " assigning this Task item in the TaskListAdapter Constructor, by returning name here: " +  (groups.get(i)).getTitle());
            Log.d(TAG, " assigning this Task item in the TaskListAdapter Constructor, by returning id here: " +  ((TaskListItem) groups.get(i)).getTaskID());
            //if(((TaskListItem) groups.get(i)).isChecked()) {
            //    taskCheckedMap.put(((TaskListItem) groups.get(i)).getTaskName(), (TaskListItem) groups.get(i));
            //}

        }
        this.accessToken = accessToken;
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

        final String thisTaskTitle = group.getTitle();
        Log.d(TAG, " assigning this Task title: " + thisTaskTitle);

        final TaskListItem myItem = taskCheckedMap.get("" + thisTaskTitle);
        Log.d(TAG, " assigning this Task item, by returning id here: " + myItem.getTaskID());

        holder.setTaskName(group);

        //in some cases, it will prevent unwanted situations
        holder.checkBox.setOnCheckedChangeListener(null);

        //if true, checkbox will be selected, else unselected
        if( myItem.isChecked()) {
            holder.checkBox.setChecked(true);
        }

        holder.checkBox.setChecked(myItem.isChecked());
        adapterVolleyParser = new VolleyParser(context, accessToken);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // set the state to the changed value
                myItem.setChecked(b);

                //increment Progress bar and send data to the API
                if(holder.checkBox.isChecked()) {
                    TaskListActivity.IncrementCompletedTasks(1);
                    adapterVolleyParser.markTaskAsCompleted(TaskListActivity.SendUserId(), myItem.getTaskID());
                }
                else {
                    TaskListActivity.IncrementCompletedTasks(-1);
                    adapterVolleyParser.markTaskAsInComplete(TaskListActivity.SendUserId(), myItem.getTaskID());
                }
            }
        });






    }
}
