package com.example.practicumapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Specific Java class used in the expandable RecyclerView on the TaskListActivity
 */

public class TaskDescriptionListItem implements Parcelable {
    private String description;

    /**
     * General constructor
     * @param description is the description of the associated TaskListItem
     */
    public TaskDescriptionListItem(String description) {
        this.description = description;
    }

    public TaskDescriptionListItem(Parcel in) {
        description = in.readString();
    }

    public static final Creator<TaskDescriptionListItem> CREATOR = new Creator<TaskDescriptionListItem>() {
        @Override
        public TaskDescriptionListItem createFromParcel(Parcel in) {
            return new TaskDescriptionListItem(in);
        }

        @Override
        public TaskDescriptionListItem[] newArray(int size) {
            return new TaskDescriptionListItem[size];
        }
    };

    /**
     * General description Getter
     * @return description
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String descriptionIn) {
        this.description = descriptionIn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        // parcel.writeString(id);
        parcel.writeString(description);
    }
}
