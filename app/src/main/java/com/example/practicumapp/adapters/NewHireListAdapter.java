package com.example.practicumapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.practicumapp.R;
import com.example.practicumapp.TaskListActivity;
import com.example.practicumapp.helpers.FilterHelper;

import java.util.ArrayList;

/**
 *  This is the new hire list activity's view adapter. It takes New Hire data in ArrayLists then
 *  creates a recyclerview. Each row in list will be launch TaskListActivity passing that row's
 *  userID and name with intent.
 *
 * @author Mitch Thompson
 * @since 1/22/2018
 * @see com.example.practicumapp.NewHireListActivity
 */

public class NewHireListAdapter extends RecyclerView.Adapter<NewHireListAdapter.NewHireListViewHolder> implements Filterable {

    private Context context;
    private View newHireView;
    private NewHireListViewHolder newHireViewHolder;
    private LayoutInflater inflater;
    private ArrayList<String> newHireName;
    private ArrayList<String> newHireID;

    //used for search & filter
    ArrayList<String> currentList;

    public NewHireListAdapter(Context newContext, ArrayList<String> newNameData, ArrayList<String> newIDData) {
        this.context = newContext;
        inflater = LayoutInflater.from(context);
        this.newHireName = newNameData;
        this.newHireID = newIDData;
        this.currentList = newHireName;

    }

    @Override
    public NewHireListViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        newHireView = LayoutInflater.from(context).inflate(R.layout.new_hire_items,parent,false);
        newHireViewHolder = new NewHireListViewHolder(newHireView);
        return newHireViewHolder;
    }

    @Override
    public void onBindViewHolder(final NewHireListViewHolder holder, int position) {
        holder.newHireBtn.setText(newHireName.get(position).toString());
        holder.newHireBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, newHireID.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), TaskListActivity.class);
//              TODO Remove sample user id intent and uncomment the userID intent below it after API data is updated
                intent.putExtra("userID", "test-id-1234");
//              intent.putExtra("userID",  newHireID.get(holder.getAdapterPosition()));
                intent.putExtra("name", newHireName.get(holder.getAdapterPosition()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return newHireName.size();
    }

    @Override
    public Filter getFilter() {
        return FilterHelper.newInstance(currentList, this);
    }

    public void setNewHireName(ArrayList<String> filteredNewHireName){

        this.newHireName = filteredNewHireName;
    }


    public static class NewHireListViewHolder extends RecyclerView.ViewHolder {
        //TextView newHireTextView;
        Button newHireBtn;

        public NewHireListViewHolder(View v) {
            super(v);
            newHireBtn = v.findViewById(R.id.new_hire_btn);
        }
    }
}
