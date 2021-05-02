package com.example.jam.controller;

import android.content.Intent;
import android.os.Bundle;

import com.example.jam.R;
import com.example.jam.model.AppDatabase;
import com.example.jam.model.JobDao;
import com.example.jam.model.UserDao;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.MenuItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class OffersListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView mRecyclerView;
    RecyclerAdapter mRecyclerAdapter;
    String monthNames[] = {"january", "february", "march", "april", "may", "june", "july", "august","september", "october", "november", "december"} ;
    private DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    Toolbar mToolbar = null;

    // Database and Dao instantiations
    public AppDatabase db;
    public JobDao jobDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_list);

        // Using the database
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "dbjam.db")
                //.createFromAsset("database/dbjam.db")
                //.createFromAsset(getDatabasePath("dbjam.db").getAbsolutePath())
                //.fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                //.addMigrations(MIGRATION_1_2)
                .build();
        jobDao = db.JobDao();
        List<Integer> myJobsList = jobDao.getAvailableJobsIds();
        Integer myList[] = new Integer[myJobsList.size()];
        //for (String n : myJobsList) {
        for (int i = 0; i < myJobsList.size(); i++) {
            myList[i] = myJobsList.get(i);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerAdapter = new RecyclerAdapter(myList, this);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mDrawerLayout = findViewById(R.id.drawLayout);
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setCheckedItem(R.id.liste);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        //this method will take care of the rottating hamburger icon when you open the drawer
        toggle.syncState();
        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }
    //we override onBackPressed because when we press the back button
    //when our navigation drawer is opened we don't want to leave the activity immediately
    //instead we need to close it first.
    @Override
    public void onBackPressed(){
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
    @SuppressWarnings("StatementWithEmptyBody")

    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.profil:
                Intent p = new Intent(OffersListActivity.this, ProfileActivity.class);
                startActivity(p);
                break;
            case R.id.carte:
                Intent c = new Intent(OffersListActivity.this, CarteEmploisActivity.class);
                startActivity(c);
                break;
            case R.id.liste:
                Intent h = new Intent(OffersListActivity.this, OffersListActivity.class);
                startActivity(h);
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
