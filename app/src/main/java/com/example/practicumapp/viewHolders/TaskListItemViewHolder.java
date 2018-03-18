package com.example.practicumapp.viewHolders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.practicumapp.R;
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
    }

    public void setTaskName(ExpandableGroup taskListItem) {
        taskName.setText(taskListItem.getTitle());
    }
/* TODO: DO I need these?
    public TextView getTaskName() {
        return taskName;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
*/

}
