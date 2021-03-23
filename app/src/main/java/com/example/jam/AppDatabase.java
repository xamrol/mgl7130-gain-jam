package com.example.jam;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Profile.class, User.class, Job.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao UserDao();

}
