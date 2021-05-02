package com.example.jam.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.jam.model.Profile;

import java.sql.Date;
import java.sql.Time;

@Entity(tableName = "users", foreignKeys = {@ForeignKey(entity = Profile.class, parentColumns = "id", childColumns = "type", onUpdate = 5, onDelete = 5)},
        indices = {@Index(value = {"type"}, unique = false)})
public class User {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "firstname")
    public String firstname;

    @ColumnInfo(name = "lastname")
    public String lastname;

    @ColumnInfo(name = "pseudo")
    @NonNull
    public String pseudo;

    @ColumnInfo(name = "email")
    @NonNull
    public String email;

    @ColumnInfo(name = "password")
    @NonNull
    public String password;

    @ColumnInfo(name = "type")
    @NonNull
    public int type;

    @ColumnInfo(name = "status", defaultValue = "1")
    @NonNull
    public int status;

    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP")
    public String createdAt;

    @ColumnInfo(name = "updated_at", defaultValue = "CURRENT_TIMESTAMP")
    public String updatedAt;

    @Ignore
    public User(String fName, String lName, String pseudo, String email, String password) {
        Date myDate = new Date(System.currentTimeMillis());
        Time myTime = new Time(System.currentTimeMillis());
        String finalDateTime = myDate.toString() + " " + myTime.toString();

        this.firstname = fName;
        this.lastname = lName;
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
        this.type = 3;
        this.status = 1;
        this.createdAt = finalDateTime;
        this.updatedAt = finalDateTime;
    }

    User(String pseudo, String email, String password, int type, int status) {
        Date myDate = new Date(System.currentTimeMillis());
        Time myTime = new Time(System.currentTimeMillis());
        String finalDateTime = myDate.toString() + " " + myTime.toString();
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
        this.type = type;
        this.status = status;
        this.createdAt = finalDateTime;
        this.updatedAt = finalDateTime;
    }

    public void setUserPassword(String newPassword) {
        this.password = newPassword;
    }

    public void setUserUpdatedAt() {
        Date myDate = new Date(System.currentTimeMillis());
        Time myTime = new Time(System.currentTimeMillis());
        String finalDateTime = myDate.toString() + " " + myTime.toString();

        this.updatedAt = finalDateTime;
    }
}
