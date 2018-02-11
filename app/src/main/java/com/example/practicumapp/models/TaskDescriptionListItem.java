package com.example.practicumapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lucasschwarz on 2/4/18.
 */

public class TaskDescriptionListItem implements Parcelable {
    private String  description;

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
