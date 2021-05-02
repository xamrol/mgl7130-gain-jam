package com.example.jam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class carteDesEmplois extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout3;
    NavigationView mNavigationView3;
    Toolbar mToolbar3 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carte_des_emplois);
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

    public boolean onNavigationItemSelected(MenuItem item){
        int id2 = item.getItemId();
        switch (id2){
            case R.id.profil:
                Intent p3 = new Intent(carteDesEmplois.this, ProfileActivity.class);
                startActivity(p3);
                break;
            case R.id.carte:
                Intent c3 = new Intent(carteDesEmplois.this, carteDesEmplois.class);
                startActivity(c3);
                break;
            case R.id.liste:
                Intent h3 = new Intent(carteDesEmplois.this, OffersListActivity.class);
                startActivity(h3);
                break;
        }
        mDrawerLayout3.closeDrawer(GravityCompat.START);
        return true;

    }
}