package com.example.jam.controller;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jam.R;
import com.example.jam.model.AppDatabase;
import com.example.jam.model.CurrentSessionDao;
import com.example.jam.model.User;
import com.example.jam.model.UserDao;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    /*
        Declarations & Initializations
     */
    private DrawerLayout mDrawerLayout2;
    NavigationView mNavigationView2;
    Toolbar mToolbar2 = null;

    private TextView mPseudo;
    private TextView mName;
    private EditText mEmail;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mPassword;
    private Button mLogoffBtn;
    private Button mActivateEditBtn;
    private Button mCancelEditBtn;
    private Button mSaveEditBtn;

    // Database and Dao instantiations
    public AppDatabase db;
    public UserDao userDao;
    public CurrentSessionDao sessionDao;

    // Other useful variables
    private String prefPseudo;
    private String prefPassword;
    private User mUser;
    protected String lToastMsg;
    protected boolean entriesAccepted = true;
    protected String lPseudo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Retrieving the interface's components
        mPseudo = (TextView) findViewById(R.id.profile_page_pseudo_txt);
        mName = (TextView) findViewById(R.id.profile_page_name_txt);
        mEmail = (EditText) findViewById(R.id.profile_email_input);
        mFirstName = (EditText) findViewById(R.id.profile_firstname_input);
        mLastName = (EditText) findViewById(R.id.profile_lastname_input);
        mPassword = (EditText) findViewById(R.id.profile_password_input);
        mLogoffBtn = (Button) findViewById(R.id.profile_page_logoff_btn);
        mActivateEditBtn = (Button) findViewById(R.id.profile_page_update_btn);
        mCancelEditBtn = (Button) findViewById(R.id.profile_page_cancel_btn);
        mSaveEditBtn = (Button) findViewById(R.id.profile_page_save_btn);

        mToolbar2 = findViewById(R.id.toolbar_profile);
        setSupportActionBar(mToolbar2);
        mDrawerLayout2 = findViewById(R.id.drawLayout_profile);
        mNavigationView2 = (NavigationView) findViewById(R.id.nav_view_profile);
        mNavigationView2.setNavigationItemSelectedListener(this);
        mNavigationView2.setCheckedItem(R.id.profil);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout2, mToolbar2,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout2.addDrawerListener(toggle);
        //this method will take care of the rotating hamburger icon when you open the drawer
        toggle.syncState();

        // Beginning
        mEmail.setEnabled(false);
        mPassword.setText("password");
        disableFields();

        // Using the database
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "dbjam.db")
                //.createFromAsset("database/dbjam.db")
                //.createFromAsset(getDatabasePath("dbjam.db").getAbsolutePath())
                //.fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                //.addMigrations(MIGRATION_1_2)
                .build();
        userDao = db.UserDao();
        sessionDao = db.CurrentSessionDao();

        // Retrieving the pseudo
        lPseudo = sessionDao.getCurrentSessionPseudo();
        mPseudo.setText(lPseudo);

        // Retrieving the user's information
        mUser = userDao.getUserByPseudo(lPseudo);
        String lFullName = mUser.getUserFirstName() + " " + mUser.getUserLastName();
        mName.setText(lFullName);
        mEmail.setText(mUser.getUserEmail());
        mFirstName.setText(mUser.getUserFirstName());
        mLastName.setText(mUser.getUserLastName());

        // Defining listeners for the buttons
        mLogoffBtn.setOnClickListener((View.OnClickListener) this);
        mActivateEditBtn.setOnClickListener((View.OnClickListener) this);
        mCancelEditBtn.setOnClickListener((View.OnClickListener) this);
        mSaveEditBtn.setOnClickListener((View.OnClickListener) this);
        mLogoffBtn.setTag(0);
        mActivateEditBtn.setTag(1);
        mCancelEditBtn.setTag(2);
        mSaveEditBtn.setTag(3);

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
                Intent c3 = new Intent(ProfileActivity.this, JobsMapActivity.class);
                startActivity(c3);
                break;
            case R.id.liste:
                Intent h2 = new Intent(ProfileActivity.this, OffersListActivity.class);
                startActivity(h2);
                break;
        }
        mDrawerLayout2.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();

        switch (responseIndex) {
            case 0:
                // mLogoffBtn
                Intent mainActivity = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(mainActivity);
                break;
            case 1:
                // mActivateEditBtn
                enableFields();
                mPassword.setText("");
                break;
            case 2:
                // mCancelEditBtn
                disableFields();
                mPassword.setText("password");
                break;
            default:
                // mSaveEditBtn
                String lPassword = mPassword.getText().toString();
                entriesAccepted = true;

                /*
                if (lPassword != null && lPassword != "password") {
                    //entriesAccepted = true;
                    if (lPassword.length() < 4) {
                        lToastMsg = "Le mot de passe doit être d'au moins 4 caractères";
                        entriesAccepted = false;
                    }
                    else {
                        entriesAccepted = true;
                        userDao.updateUserPassword(hashStringUsingBCrypt(lPassword), lPseudo);
                    }
                }
                */

                if (lPassword.length() < 4) {
                    lToastMsg = "Le mot de passe doit être d'au moins 4 caractères";
                    entriesAccepted = false;
                }
                else {
                    entriesAccepted = true;
                    userDao.updateUserPassword(hashStringUsingBCrypt(lPassword), lPseudo);
                }


                if (entriesAccepted) {
                    lToastMsg = "Succès";
                    String fName = mFirstName.getText().toString();
                    String lName = mLastName.getText().toString();
                    userDao.updateUserFirstName(fName, lPseudo);
                    userDao.updateUserLastName(lName, lPseudo);
                    mName.setText(fName + " " + lName);
                    disableFields();

                }

                Toast.makeText(this, lToastMsg, Toast.LENGTH_SHORT).show();

        }
    }

    public void enableFields() {
        mCancelEditBtn.setEnabled(true);
        mSaveEditBtn.setEnabled(true);
        mFirstName.setEnabled(true);
        mLastName.setEnabled(true);
        mPassword.setEnabled(true);
    }

    public void disableFields() {
        mCancelEditBtn.setEnabled(false);
        mSaveEditBtn.setEnabled(false);
        mFirstName.setEnabled(false);
        mLastName.setEnabled(false);
        mPassword.setEnabled(false);
    }

    protected String hashStringUsingBCrypt(String entry) {
        String encryptedValue = BCrypt.withDefaults().hashToString(12, entry.toCharArray());

        return encryptedValue;
    }

}
