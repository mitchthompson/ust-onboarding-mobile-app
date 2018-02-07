package com.example.practicumapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lucasschwarz on 2/4/18.
 */

public class TaskDescription implements Parcelable {
    private String id, description;

    public TaskDescription(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public TaskDescription(Parcel in) {
        id = in.readString();
        description = in.readString();
    }

    public static final Creator<TaskDescription> CREATOR = new Creator<TaskDescription>() {
        @Override
        public TaskDescription createFromParcel(Parcel in) {
            return new TaskDescription(in);
        }

        @Override
        public TaskDescription[] newArray(int size) {
            return new TaskDescription[size];
        }
    };

    public String getId() {
        return id;
    }

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
        parcel.writeString(id);
        parcel.writeString(description);
    }
}
