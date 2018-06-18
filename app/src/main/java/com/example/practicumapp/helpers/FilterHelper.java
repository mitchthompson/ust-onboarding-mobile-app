package com.example.practicumapp.helpers;

import android.widget.Filter;
import java.util.ArrayList;
import android.support.v7.widget.RecyclerView;

import com.example.practicumapp.adapters.NewHireListAdapter;

/**
 * Filters recycler view search field for NewHireListActivity
 * @author Bonnie Peterson
 */

public class FilterHelper extends Filter {

    static ArrayList<String> currentList;
    static NewHireListAdapter adapter;

    public static FilterHelper newInstance(ArrayList<String> currentList, NewHireListAdapter adapter){
        FilterHelper.adapter = adapter;
        FilterHelper.currentList = currentList;
        return new FilterHelper();
    }

    /**
     * Checks for text entry in the search field and filters it
     * @param constraint
     * @return filterResults
     */
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults = new FilterResults();

        if(constraint != null && constraint.length() > 0){

            //change to upper case
            constraint = constraint.toString().toUpperCase();

            //hold filters we find
            ArrayList<String> foundFilters = new ArrayList<>();

            String newHireName;

            //iterate through current list
            for (int i = 0; i < currentList.size(); i++){

                newHireName = currentList.get(i);

                //search
                if (newHireName.toUpperCase().contains(constraint)){

                    //add to new array if found
                    foundFilters.add(newHireName);
                }
            }

            //set results to filter list
            filterResults.count = foundFilters.size();
            filterResults.values = foundFilters;

        } else {

            //no item found, list remains intact
            filterResults.count = currentList.size();
            filterResults.values = currentList;
        }

        return filterResults;
    }

    //updates the recycler view to show the new filtered list
    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        adapter.setNewHireName((ArrayList<String>) filterResults.values);
        adapter.notifyDataSetChanged();

    }
}
