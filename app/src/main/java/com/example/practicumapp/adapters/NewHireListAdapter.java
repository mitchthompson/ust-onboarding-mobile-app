package com.example.practicumapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.practicumapp.R;

import java.util.ArrayList;

/**
 *  This is the new hire list activity's view adapter. It takes New Hire data in ArrayList then
 *  displays creates a recyclerview.
 *
 * @author Mitch Thompson
 * @since 1/22/2018
 * @see com.example.practicumapp.NewHireListActivity
 */

public class NewHireListAdapter extends RecyclerView.Adapter<NewHireListAdapter.NewHireListViewHolder> {

    private Context context;
    private View newHireView;
    private NewHireListViewHolder newHireViewHolder;
    private LayoutInflater inflater;
    private ArrayList<String> newHireData;

    public NewHireListAdapter(Context newContext, ArrayList<String> newData) {
        this.context = newContext;
        inflater = LayoutInflater.from(context);
        this.newHireData = newData;
    }

    @Override
    public NewHireListViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        newHireView = LayoutInflater.from(context).inflate(R.layout.new_hire_items,parent,false);
        newHireViewHolder = new NewHireListViewHolder(newHireView);
        return newHireViewHolder;
    }

    @Override
    public void onBindViewHolder(NewHireListViewHolder holder, int position) {
        //holder.newHireTextView.setText(newHireData.get(position).toString());
        holder.newHireBtn.setText(newHireData.get(position).toString());
        holder.newHireBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "User ID", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return newHireData.size();
    }


    public static class NewHireListViewHolder extends RecyclerView.ViewHolder {
        //TextView newHireTextView;
        Button newHireBtn;
        public NewHireListViewHolder(View v) {
            super(v);
            //newHireTextView = v.findViewById(R.id.new_hire_list_item);
            newHireBtn = v.findViewById(R.id.new_hire_btn);
        }
    }
}
