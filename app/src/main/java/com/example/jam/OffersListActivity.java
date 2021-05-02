package com.example.jam;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;

public class OffersListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    RecyclerView mRecyclerView;
    RecyclerAdapter mRecyclerAdapter;
    String monthNames[] = {"january", "february", "march", "april", "may", "june", "july", "august","september", "october", "november", "december"} ;
    private DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    Toolbar mToolbar = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerAdapter = new RecyclerAdapter(monthNames, this);
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
                Intent c = new Intent(OffersListActivity.this, carteDesEmplois.class);
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
