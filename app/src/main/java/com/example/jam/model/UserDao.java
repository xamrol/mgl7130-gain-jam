package com.example.jam.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.jam.model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Query("SELECT * FROM users WHERE type = :profileId")
    List<User> getUsersByType(int profileId);

    @Query("SELECT COUNT(*) FROM users")
    int getNumberOfUsers();

    @Query("SELECT COUNT(*) FROM users WHERE type = :profileId")
    int getNumberOfUsersByType(int profileId);

    @Query("SELECT * FROM users WHERE id in (:usersIds)")
    List<User> retrieveUsersByIds(int[] usersIds);

    @Query("SELECT * FROM users WHERE pseudo = :userPseudo")
    User getUserByPseudo(String userPseudo);

    @Query("SELECT DISTINCT pseudo FROM users WHERE type = :profileId")
    List<String> getDistinctPseudosByType(int profileId);

    @Query("SELECT DISTINCT pseudo FROM users")
    List<String> getDistinctPseudos();

    @Query("SELECT DISTINCT email FROM users WHERE type = :profileId")
    List<String> getDistinctEmailsByType(int profileId);

    @Query("SELECT DISTINCT email FROM users")
    List<String> getDistinctEmails();

    @Query("SELECT password FROM users WHERE pseudo = :username")
    String getPasswordByPseudo(String username);

    @Query("SELECT status FROM users WHERE pseudo = :username")
    int getStatusByPseudo(String username);

    @Query("UPDATE users SET status = 0 WHERE pseudo = :userPseudo")
    void deactivateUserAccount(String userPseudo);

    @Query("UPDATE users SET status = 1 WHERE pseudo = :userPseudo")
    void activateUserAccount(String userPseudo);

    @Query("UPDATE users SET password = :newPassword WHERE pseudo = :userPseudo")
    void updateUserPassword(String newPassword, String userPseudo);

    @Query("UPDATE users SET firstname = :newFirstName WHERE pseudo = :userPseudo")
    void updateUserFirstName(String newFirstName, String userPseudo);

    @Query("UPDATE users SET lastname = :newLastName WHERE pseudo = :userPseudo")
    void updateUserLastName(String newLastName, String userPseudo);

    @Query("INSERT INTO users(firstname,lastname,pseudo,email,password,type) VALUES(:userFirstName,:userLastName,:userPseudo,:userEmail,:userPassword,:userType)")
    void createNewUser(String userFirstName, String userLastName, String userPseudo, String userEmail, String userPassword, int userType);

    @Update
    void updateUsers(User... users);

    @Update
    void updateUser(User user);

    @Insert
    public void insertUsers(User... users);

}
