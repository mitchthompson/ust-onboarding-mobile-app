package com.example.practicumapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.practicumapp.R;

import java.util.ArrayList;

/**
 * Created by Mitch Thompson on 1/22/18.
 *
 * This is the new hire list activity's view adapter. It takes data and displays in a recyclerview.
 *
 */

public class NewHireListAdapter extends RecyclerView.Adapter<NewHireListAdapter.NewHireListViewHolder> {

    private Context context;
    private View newHireView;
    private NewHireListViewHolder newHireViewHolder;
    private LayoutInflater inflater;
    private ArrayList<String> newHireData = new ArrayList();

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
        holder.newHireTextView.setText(newHireData.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return newHireData.size();
    }

    public static class NewHireListViewHolder extends RecyclerView.ViewHolder {
        public TextView newHireTextView;
        public NewHireListViewHolder(View v) {
            super(v);
            newHireTextView = (TextView) v.findViewById(R.id.new_hire_list_item);
        }
    }
}
