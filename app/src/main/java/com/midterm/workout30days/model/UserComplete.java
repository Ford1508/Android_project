package com.midterm.workout30days.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class UserComplete implements Serializable {

    @PrimaryKey
    private int day_id;

    @ColumnInfo
    private int day_complete;

    public UserComplete(int day_id, int day_complete) {
        this.day_id = day_id;
        this.day_complete = day_complete;
    }

    public int getDay_id() {
        return day_id;
    }

    public void setDay_id(int day_id) {
        this.day_id = day_id;
    }

    public int getDay_complete() {
        return day_complete;
    }

    public void setDay_complete(int day_complete) {
        this.day_complete = day_complete;
    }
}
