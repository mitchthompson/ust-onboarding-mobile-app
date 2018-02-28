package com.example.practicumapp;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

/**
 * Created by lucasschwarz on 2/10/18.
 */

public class TaskListItemViewHolder extends GroupViewHolder {

    public CheckBox checkBox;
    private TextView taskName;


    public TaskListItemViewHolder(View itemView) {
        super(itemView);
        checkBox = (CheckBox) itemView.findViewById(R.id.task_checkbox);
        taskName = (TextView) itemView.findViewById(R.id.task_name);

        checkBox.setOnCheckedChangeListener(null);

        checkBox.setChecked(checkBox.isSelected());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkBox.isChecked()) {
                    TaskListActivity.ProgressBarIncrement(1);
                }
                else {
                    TaskListActivity.ProgressBarIncrement(-1);
                }
            }
        });
    }

    public void setTaskName(ExpandableGroup taskListItem) {
        taskName.setText(taskListItem.getTitle());
    }
}
