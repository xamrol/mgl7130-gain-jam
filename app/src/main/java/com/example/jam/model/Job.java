package com.example.jam.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
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

    @ColumnInfo(name = "preferences")
    public String preferences;

    @ColumnInfo(name = "working_hours")
    public String working_hours;

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


    public Job(String title, String employer, String contact, String category) {
        this.title = title;
        this.employer = employer;
        this.contact = contact;
        this.category = category;
    }

    public Job(Job myJob) {
        this.id = myJob.id;
        this.title = myJob.title;
        this.employer = myJob.employer;
        this.contact = myJob.contact;
        this.category = myJob.category;
        this.status = myJob.status;
        this.preferences = myJob.preferences;
        this.working_hours = myJob.working_hours;
        this.requirement1 = myJob.requirement1;
        this.requirement2 = myJob.requirement2;
        this.requirement3 = myJob.requirement3;
        this.location1 = myJob.location1;
        this.location2 = myJob.location2;
        this.location3 = myJob.location3;
        this.location4 = myJob.location4;
        this.createdAt = myJob.createdAt;
        this.updatedAt = myJob.updatedAt;
    }

    public int getJobId() { return this.id; }

    public String getJobTitle() {
        return this.title;
    }

    public String getJobCompany() {
        return this.employer;
    }

    public String getJobWorkingHours() {
        return this.working_hours;
    }

    public String getJobCategory() { return this.category; }

    public String getJobContact() { return this.contact; }

    public String getJobPreferences() { return  this.preferences; }

    public String getJobRequirement1() { return this.requirement1; }

    public String getJobRequirement2() { return this.requirement2; }

    public String getJobRequirement3() { return this.requirement3; }

    public String getJobLatitude() { return this.location1; }

    public String getJobLongitude() { return this.location2; }

    public int getJobStatus() { return this.status; }
}
