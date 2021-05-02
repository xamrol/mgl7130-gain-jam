package com.example.jam.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.jam.model.Job;
import com.example.jam.model.Profile;
import com.example.jam.model.User;
import com.example.jam.model.UserDao;

@Database(entities = {Profile.class, User.class, Job.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao UserDao();

    public abstract JobDao JobDao();

}
