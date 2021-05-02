package com.example.jam;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout2;
    NavigationView mNavigationView2;
    Toolbar mToolbar2 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mToolbar2 = findViewById(R.id.toolbar_profile);
        setSupportActionBar(mToolbar2);
        mDrawerLayout2 = findViewById(R.id.drawLayout_profile);
        mNavigationView2 = (NavigationView) findViewById(R.id.nav_view_profile);
        mNavigationView2.setNavigationItemSelectedListener(this);
        mNavigationView2.setCheckedItem(R.id.profil);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout2, mToolbar2,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout2.addDrawerListener(toggle);
        //this method will take care of the rottating hamburger icon when you open the drawer
        toggle.syncState();
    }
    @Override
    public void onBackPressed(){
        if (mDrawerLayout2.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout2.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
    public boolean onNavigationItemSelected(MenuItem item){
        int id2 = item.getItemId();
        switch (id2){
            case R.id.profil:
                Intent p2 = new Intent(ProfileActivity.this, ProfileActivity.class);
                startActivity(p2);
                break;
            case R.id.carte:
                Intent c2 = new Intent(ProfileActivity.this, carteDesEmplois.class);
                startActivity(c2);
                break;
            case R.id.liste:
                Intent h2 = new Intent(ProfileActivity.this, OffersListActivity.class);
                startActivity(h2);
                break;
        }
        mDrawerLayout2.closeDrawer(GravityCompat.START);
        return true;

    }


}
