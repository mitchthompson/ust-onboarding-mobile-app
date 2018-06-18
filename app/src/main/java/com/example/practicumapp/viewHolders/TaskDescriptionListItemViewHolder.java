package com.example.practicumapp.viewHolders;

import android.view.View;
import android.widget.TextView;

import com.example.practicumapp.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * Created by lucasschwarz on 2/10/18.
 */

public class TaskDescriptionListItemViewHolder extends ChildViewHolder {

    private TextView childTextView;

    public TaskDescriptionListItemViewHolder(View itemView) {
        super(itemView);
        childTextView = (TextView) itemView.findViewById(R.id.task_description);
    }

    public void setChildTextView(String description) {
        childTextView.setText(description);
    }
}
