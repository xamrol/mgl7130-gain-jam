package com.example.jam.model;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CurrentSessionDao {
    @Query("SELECT COUNT(*) FROM current_session")
    int getNumberOfCurrentSessions();

    @Query("SELECT pseudo FROM current_session WHERE id = 1")
    String getCurrentSessionPseudo();

    @Query("UPDATE current_session SET pseudo = :userPseudo WHERE id = 1")
    void saveCurrentSessionPseudo(String userPseudo);
}
