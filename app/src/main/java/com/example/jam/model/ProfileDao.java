package com.example.jam.model;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProfileDao {
    @Query("SELECT * FROM profiles")
    List<Profile> getAllProfiles();

    @Query("SELECT COUNT(*) FROM profiles")
    int getNumberOfProfiles();

    @Query("SELECT name FROM profiles WHERE id = :profileId")
    String getProfileName(int profileId);

    @Query("SELECT `desc` FROM profiles WHERE id = :profileId")
    String getProfileDesc(int profileId);

    @Query("SELECT `desc` FROM profiles")
    List<String> getAllProfilesDesc();
}
