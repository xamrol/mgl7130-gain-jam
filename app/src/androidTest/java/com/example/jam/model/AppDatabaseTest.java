package com.example.jam.model;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import at.favre.lib.crypto.bcrypt.BCrypt;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class AppDatabaseTest extends TestCase {
    // Declarations
    public AppDatabase db;
    public CurrentSessionDao cSessionDao;
    public ProfileDao profileDao;
    public UserDao userDao;

    @Before
    public void setUp() throws Exception {
        //super.setUp();
        Context context = ApplicationProvider.getApplicationContext();
        //db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        db = Room.databaseBuilder(context, AppDatabase.class, "dbjam-test.db")
                .createFromAsset("database/dbjam-test.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        cSessionDao = db.CurrentSessionDao();
        profileDao = db.ProfileDao();
        userDao = db.UserDao();
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void testCurrentSessionRecords() {
        assertEquals(1, cSessionDao.getNumberOfCurrentSessions());
    }

    @Test
    public void testProfileRecords() {
        assertEquals(3, profileDao.getNumberOfProfiles());
    }

    @Test
    public void testProfileName() {
        assertTrue(profileDao.getProfileName(1).contains("mi"));
    }

    @Test
    public void testProfilesDesc() {
        List<String> lAllDescriptions = profileDao.getAllProfilesDesc();
        for(String desc : lAllDescriptions) {
            assertTrue("Description contains 'profile'", desc.contains("profile"));
        }
    }

    @Test
    public void testUserPassword() {
        String hash = userDao.getPasswordByPseudo("test0");
        assertTrue("Correct password!", verifyEntryAndHashValue("test0", hash));
    }

    @Test
    public void testUserStatus() {
        userDao.deactivateUserAccount("test2");
        assertEquals("Account deactivated!", 0, userDao.getStatusByPseudo("test2"));
    }

    public boolean verifyEntryAndHashValue(String entry, String hashValue) {
        BCrypt.Result result = BCrypt.verifyer().verify(entry.toCharArray(), hashValue);

        return result.verified;
    }
}