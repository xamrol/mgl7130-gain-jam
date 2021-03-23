package com.example.jam;

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

import java.util.List;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordResetFinalActivity extends AppCompatActivity implements View.OnClickListener {

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
    private String mPseudo;
    private String message;
    private boolean entryAccepted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset_final);

        // Retrieving the interface's components
        mPageInfo = (TextView) findViewById(R.id.reset_page_info_txt);
        mPageInput = (EditText) findViewById(R.id.reset_page_main_input);
        mPageButton = (Button) findViewById(R.id.reset_page_main_btn);

        // Beginning
        mPageButton.setEnabled(false);
        mPageButton.setText("Enregistrer");
        mPageInput.setHint("Mot de passe");
        mPageInfo.setText("Saisissez votre nouveau mot de passe (4 caractères au moins)");

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

        // Retrieving a previous activity extras
        Intent pResetFinalActivity = getIntent();
        if (pResetFinalActivity != null) {
            if (pResetFinalActivity.hasExtra("userGivenPseudo")) {
                mPseudo = pResetFinalActivity.getStringExtra("userGivenPseudo");
                Toast.makeText(this, mPseudo, Toast.LENGTH_SHORT).show();
            }
        }

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

        if (userInput.length() >= 4) {
            message = "Succès";
            entryAccepted = true;
        }
        else {
            message = "Mot de passe trop court";
            entryAccepted = false;
            //mPageInput.setText("");
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (entryAccepted) {
                    // Updating the entry in our database
                    userDao.updateUserPassword(hashStringUsingBCrypt(userInput), mPseudo);
                    //User localUser = userDao.getUserByPseudo(mPseudo);
                    //localUser.setUserPassword(hashStringUsingBCrypt(userInput));
                    //localUser.setUserUpdatedAt();
                    //userDao.updateUser(localUser);

                    // We redirect the user to the login page
                    Intent loginActivity = new Intent(PasswordResetFinalActivity.this, LoginActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("subscriberPseudo", mPseudo);
                    bundle.putString("subscriberPassword", userInput);
                    loginActivity.putExtras(bundle);
                    startActivity(loginActivity);
                }
            }
        }, 2000);
    }

    protected String hashStringUsingBCrypt(String entry) {
        String encryptedValue = BCrypt.withDefaults().hashToString(12, entry.toCharArray());

        return encryptedValue;
    }
}