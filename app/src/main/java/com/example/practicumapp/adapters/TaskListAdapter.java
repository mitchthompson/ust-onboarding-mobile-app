package com.example.practicumapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.practicumapp.R;
import com.example.practicumapp.models.Tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jsayler on 1/14/18.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder> {

    private Context taskContext;
    private View taskView;
    private TaskListViewHolder taskViewHolder;
    private LayoutInflater inflater;
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
    }

    @Override
    public int getItemCount() {
        return taskData.size();
    }

    public static class TaskListViewHolder extends RecyclerView.ViewHolder {
        public TextView taskTextView;
        public TaskListViewHolder(View v) {
            super(v);
            taskTextView = (TextView) v.findViewById(R.id.task_list_item);
        }
    }
}
