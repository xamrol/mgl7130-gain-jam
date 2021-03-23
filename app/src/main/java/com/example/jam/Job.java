package com.example.jam;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "jobs")
public class Job {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    @NonNull
    public String title;

    @ColumnInfo(name = "employer")
    @NonNull
    public String employer;

    @ColumnInfo(name = "contact")
    @NonNull
    public String contact;

    @ColumnInfo(name = "category")
    @NonNull
    public String category;

    @ColumnInfo(name = "status", defaultValue = "1")
    @NonNull
    public int status;

    @ColumnInfo(name = "requirement1")
    public String requirement1;

    @ColumnInfo(name = "requirement2")
    public String requirement2;

    @ColumnInfo(name = "requirement3")
    public String requirement3;

    @ColumnInfo(name = "location1")
    public String location1;

    @ColumnInfo(name = "location2")
    public String location2;

    @ColumnInfo(name = "location3")
    public String location3;

    @ColumnInfo(name = "location4")
    public String location4;

    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP")
    public String createdAt;

    @ColumnInfo(name = "updated_at", defaultValue = "CURRENT_TIMESTAMP")
    public String updatedAt;

    Job() {
        //
    }
}
