package com.example.jam.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jam.R;
import com.example.jam.model.AppDatabase;

import java.util.List;

import at.favre.lib.crypto.bcrypt.BCrypt;

import com.example.jam.model.User;
import com.example.jam.model.UserDao;

public class SubscriptionActivity extends AppCompatActivity implements View.OnClickListener {

    /*
        Declarations and Initializations
     */
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mPassword;
    private Button mRegisterButton;

    // Keys for the persistent data
    private static final String PREFERENCES_KEY_USERNAME = "pseudo";
    private static final String PREFERENCES_KEY_PASSWORD = "password";

    // Database and Dao instantiations
    public AppDatabase db;
    public UserDao userDao;

    // Other useful variables
    private String message;
    private boolean entriesAccepted;
    private List<String> mExistingEmails;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Empty implementation, because the schema isn't changing.
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        // Using the database
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "dbjam.db")
                //.createFromAsset("database/dbjam.db")
                //.fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                //.addMigrations(MIGRATION_1_2)
                .build();
        userDao = db.UserDao();

        // Retrieving the existing emails
        retrieveAllExistingEmails();

        mFirstName = (EditText) findViewById(R.id.register_page_firstname_input);
        mLastName = (EditText) findViewById(R.id.register_page_lastname_input);
        mEmail = (EditText) findViewById(R.id.register_page_email_input);
        mPassword = (EditText) findViewById(R.id.register_page_password_input);
        mRegisterButton = (Button) findViewById(R.id.register_page_main_btn);

        mRegisterButton.setEnabled(false);

        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRegisterButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Defining a listener for the main button
        mRegisterButton.setOnClickListener((View.OnClickListener) this);
        mRegisterButton.setTag(0);
    }

    public void showGoogleLoginPage(View view) {
        Toast.makeText(getApplicationContext(), "Fonctionnalité à venir", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initializeFields();
            }
        }, 2000);
    }

    public void showFacebookLoginPage(View view) {
        Toast.makeText(getApplicationContext(), "Fonctionnalité à venir", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initializeFields();
            }
        }, 2000);
    }

    public void initializeFields() {
        mFirstName.setText("");
        mLastName.setText("");
        mEmail.setText("");
        mPassword.setText("");
        mRegisterButton.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        String fName = mFirstName.getText().toString();
        String lName = mLastName.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        message = "Informations enregistrées";
        entriesAccepted = true;

        // All the fields are required and must be filled
        if (fName.length()==0 || lName.length()==0 || email.length()==0 || password.length()==0) {
        //if (fName=="" | lName=="" | email=="" | password=="") {
            message = "Veuillez remplir tous les champs";
            entriesAccepted = false;
        }

        // The email must be unique
        if (email.length()>0 && mExistingEmails.contains(email.toLowerCase())) {
        //if (mExistingEmails.contains(email.toLowerCase())) {
            message = "Ce courriel existe déjà";
            entriesAccepted = false;
        }

        // The password must be at least 4 characters long
        if (password.length() < 4) {
            message = "Le mot de passe doit contenir au moins 4 caractères";
            entriesAccepted = false;
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (entriesAccepted) {
                    // Creating the persistent data
                    //SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                    //preferences.edit().putString(PREFERENCES_KEY_USERNAME, mEmail.getText().toString()).apply();
                    //preferences.edit().putString(PREFERENCES_KEY_PASSWORD, mPassword.getText().toString()).apply();

                    // We must save the data set in our database
                    User mUser = new User(fName, lName, email, email, hashStringUsingBCrypt(password));
                    userDao.insertUsers(mUser);
                    //userDao.createNewUser(fName, lName, email, email, hashStringUsingBCrypt(password),3);

                    // We redirect the user to the login page
                    Intent loginActivity = new Intent(SubscriptionActivity.this, LoginActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("subscriberPseudo", email);
                    bundle.putString("subscriberPassword", password);
                    loginActivity.putExtras(bundle);
                    startActivity(loginActivity);
                }
            }
        }, 2000);
    }

    protected void retrieveAllExistingEmails() {
        if(userDao.getNumberOfUsers() > 0) {
            mExistingEmails = userDao.getDistinctEmails();
        }
    }

    protected String hashStringUsingBCrypt(String entry) {
        String encryptedValue = BCrypt.withDefaults().hashToString(12, entry.toCharArray());

        return encryptedValue;
    }

}