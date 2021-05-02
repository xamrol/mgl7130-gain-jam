package com.example.jam.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jam.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /*
        Declarations & Initializations
    */
    private Button mLoginButton;
    private Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieving the interface's components
        mLoginButton = (Button) findViewById(R.id.main_page_login_btn);
        mRegisterButton = (Button) findViewById(R.id.main_page_register_btn);


        // Defining a listener for the buttons
        mLoginButton.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);

        // Usage of the tag property to name the buttons
        mLoginButton.setTag(0);
        mRegisterButton.setTag(1);
    }

    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();

        if (responseIndex == 0) {
            Intent loginActivity = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginActivity);
        }
        else {
            Intent subscriptionActivity = new Intent(MainActivity.this, SubscriptionActivity.class);
            startActivity(subscriptionActivity);
        }

    }

}