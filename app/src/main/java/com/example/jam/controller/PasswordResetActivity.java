package com.example.jam.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jam.model.AppDatabase;
import com.example.jam.R;
import com.example.jam.model.UserDao;

import java.util.List;

public class PasswordResetActivity extends AppCompatActivity implements View.OnClickListener {

    /*
      Declarations & Initializations
    */
    private EditText mPageInput;
    private Button mPageButton;
    private TextView mPageInfo;

    // Database and Dao instantiations
    public AppDatabase db;
    public UserDao userDao;

    // Other useful variables
    private List<String> existingPseudos;
    private String message;
    private boolean entryAccepted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

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

        // Retrieve the existing pseudos
        retrieveAllExistingPseudos();

        // Retrieving the interface's components
        mPageInfo = (TextView) findViewById(R.id.reset_page_info_txt);
        mPageInput = (EditText) findViewById(R.id.reset_page_main_input);
        mPageButton = (Button) findViewById(R.id.reset_page_main_btn);

        // Beginning
        //mPageInput.setInputType(1); // 1: TYPE_CLASS_TEXT, 2: TYPE_CLASS_NUMBER, 128: TYPE_TEXT_VARIATION_PASSWORD
        mPageButton.setEnabled(false);

        // Checking the user input
        mPageInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPageButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Defining a listener for the main button
        mPageButton.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        String userInput = mPageInput.getText().toString().toLowerCase();

        if (existingPseudos.contains(userInput)) {
            message = "Succès";
            entryAccepted = true;
        }
        else {
            message = "Pseudo non reconnu";
            entryAccepted = false;
            mPageInput.setText("");
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (entryAccepted) {
                    // We redirect the user to the login page
                    Intent pResetCodeActivity = new Intent(PasswordResetActivity.this, PasswordResetCodeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("userInput", userInput);
                    pResetCodeActivity.putExtras(bundle);
                    startActivity(pResetCodeActivity);
                }
            }
        }, 2000);

    }

    protected void retrieveAllExistingPseudos() {
        if(userDao.getNumberOfUsers() > 0) {
            existingPseudos = userDao.getDistinctPseudos();
        }
    }

}