package com.example.jam.model;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface JobDao {
    @Query("SELECT * FROM jobs")
    List<Job> getAllJobs();

    @Query("SELECT * FROM jobs WHERE status = 1")
    List<Job> getAvailableJobs();

    @Query("SELECT * FROM jobs WHERE employer = :companyName")
    List<Job> getJobsByCompany(String companyName);

    @Query("SELECT COUNT(*) FROM jobs")
    int getNumberOfJobs();

    @Query("SELECT COUNT(*) FROM jobs WHERE status = 1")
    int getNumberOfAvailableJobs();

    @Query("SELECT title FROM jobs WHERE status = 1")
    List<String> getAvailableJobsTitles();

    @Query("SELECT id FROM jobs WHERE status = 1")
    List<Integer> getAvailableJobsIds();

    @Query("SELECT * FROM jobs WHERE id = :jobId")
    Job getJobById(Integer jobId);

}
