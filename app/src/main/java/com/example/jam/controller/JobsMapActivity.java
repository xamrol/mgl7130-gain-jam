package com.example.jam.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.jam.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jam.model.AppDatabase;
import com.example.jam.model.Job;
import com.example.jam.model.JobDao;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Map;

public class JobsMapActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private DrawerLayout mDrawerLayout3;
    NavigationView mNavigationView3;
    Toolbar mToolbar3 = null;

    private GoogleMap mMap;

    // Database and Dao instantiations
    public AppDatabase db;
    public JobDao jobDao;

    // Other useful variables
    private List<Job> jobList;
    protected Map<Marker, Integer> mMarkerMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_map);

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

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mToolbar3 = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar3);
        mDrawerLayout3 = findViewById(R.id.drawLayout_carte_emplois);
        mNavigationView3 = (NavigationView) findViewById(R.id.nav_view_carte);
        mNavigationView3.setNavigationItemSelectedListener(this);
        mNavigationView3.setCheckedItem(R.id.carte);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout3, mToolbar3,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout3.addDrawerListener(toggle);
        //this method will take care of the rottating hamburger icon when you open the drawer
        toggle.syncState();
    }

    @Override
    public void onBackPressed(){
        if (mDrawerLayout3.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout3.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id2 = item.getItemId();
        switch (id2){
            case R.id.profil:
                Intent p3 = new Intent(JobsMapActivity.this, ProfileActivity.class);
                startActivity(p3);
                break;
            case R.id.carte:
                Intent c3 = new Intent(JobsMapActivity.this, JobsMapActivity.class);
                startActivity(c3);
                break;
            case R.id.liste:
                Intent h3 = new Intent(JobsMapActivity.this, OffersListActivity.class);
                startActivity(h3);
                break;
        }
        mDrawerLayout3.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
        {

            @Override
            public void onInfoWindowClick(Marker marker) {
                //if(marker != null && marker.getTitle().equals("English")) {
                if(marker != null) {
                    Intent jobsMapActivity = new Intent(JobsMapActivity.this, JobDetailsActivity.class);
                    jobsMapActivity.putExtra("jobId", (int) marker.getTag());
                    startActivity(jobsMapActivity);
                }
            }
        });

        /*
        // Add a marker in Sydney
        LatLng sydney = new LatLng(-33.852, 151.211);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Montreal"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */

        jobList = jobDao.getAvailableJobs();
        LatLng position;
        String title;
        Marker marker;

        for(Job job : jobList) {
            float latitude = Float.parseFloat(job.getJobLatitude());
            float longitude = Float.parseFloat(job.getJobLongitude());
            position = new LatLng(latitude, longitude);
            title = job.getJobTitle();

            mMap.addMarker(new MarkerOptions().position(position).title(title)).setTag(job.getJobId());
            //marker = mMap.addMarker(new MarkerOptions().position(position).title(title));

            //mMarkerMap.put(marker, job.getJobId());

        }

    }

    /*
    @Override
    public boolean onMarkerClick(Marker marker) {
        //return false;
        //Toast.makeText(this, mMarkerMap.get(marker).toString(), Toast.LENGTH_SHORT).show();

        Intent jobsMapActivity = new Intent(JobsMapActivity.this, JobDetailsActivity.class);
        jobsMapActivity.putExtra("jobId", 1);
        startActivity(jobsMapActivity);

        return true;
    }
    */
}