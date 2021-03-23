package com.example.jam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import at.favre.lib.crypto.bcrypt.BCrypt;

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