package com.example.jam.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "current_session")
public class CurrentSession {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    @ColumnInfo(name = "pseudo")
    public String pseudo;

    CurrentSession() {
        //
    }
}
