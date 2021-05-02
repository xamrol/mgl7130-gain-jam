package com.example.jam.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

import com.example.jam.model.AppDatabase;
import com.example.jam.R;
import com.example.jam.model.UserDao;

import java.util.List;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /*
        Declarations & Initializations
    */
    private EditText mUsername;
    private EditText mPassword;
    private Button mLoginButton;
    private TextView mRegisterLink;
    private TextView mResetPasswordLink;
    private ImageView mFacebookLogin;
    private ImageView mGoogleLogin;

    // We define IDs for other activities
    private static final int SUBSCRIPTION_ACTIVITY_REQUEST_CODE = 42;
    private static final int RESET_ACTIVITY_REQUEST_CODE = 52;
    private static final int JOB_ACTIVITY_REQUEST_CODE = 62;

    // Keys for the persistent data
    private static final String PREFERENCES_KEY_USERNAME = "pseudo";
    private static final String PREFERENCES_KEY_PASSWORD = "password";

    // Database and Dao instantiations
    public AppDatabase db;
    public UserDao userDao;

    // Other useful variables
    private List<String> existingPseudos;
    private boolean credentialsFound = false;
    private String prefPseudo;
    private String prefPassword;

    /*static final Migration MIGRATION_1_2 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Empty implementation, because the schema isn't changing.
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        // Retrieving the existing pseudos
        retrieveAllExistingPseudos();

        // Retrieving the interface's components
        mUsername = (EditText) findViewById(R.id.login_page_pseudo_input);
        mPassword = (EditText) findViewById(R.id.login_page_password_input);
        mLoginButton = (Button) findViewById(R.id.login_page_main_btn);
        mRegisterLink = (TextView) findViewById(R.id.login_page_register_txt);
        mResetPasswordLink = (TextView) findViewById(R.id.login_page_reset_txt);
        mFacebookLogin = (ImageView) findViewById(R.id.login_page_facebook_img);
        mGoogleLogin = (ImageView) findViewById(R.id.login_page_google_img);

        // Beginning
        mLoginButton.setEnabled(false);

        // Making the reset and subscription texts clickable
        /*
        String passwordResetString = "Mot de passe oublié ?";
        SpannableString ss = new SpannableString(passwordResetString);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {

            }
        };
        ss.setSpan(clickableSpan1, 0, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        */


        // Retrieving a previous activity extras or user Preferences
        Intent loginActivity = getIntent();
        if (loginActivity != null) {
            String subscriberPseudo = "";
            String subscriberPassword = "";
            if (loginActivity.hasExtra("subscriberPseudo")) {
                subscriberPseudo = loginActivity.getStringExtra("subscriberPseudo");
                subscriberPassword = loginActivity.getStringExtra("subscriberPassword");

                // Saving the user's data as Preferences
                SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                preferences.edit().putString(PREFERENCES_KEY_USERNAME, subscriberPseudo).apply();
                preferences.edit().putString(PREFERENCES_KEY_PASSWORD, subscriberPassword).apply();
            }
        }
        prefPseudo = getPreferences(MODE_PRIVATE).getString(PREFERENCES_KEY_USERNAME, null);
        prefPassword = getPreferences(MODE_PRIVATE).getString(PREFERENCES_KEY_PASSWORD, null);
        if (prefPseudo != null) {
            mUsername.setText(prefPseudo);
            mPassword.setText(prefPassword);
            mLoginButton.setEnabled(true);
        }


        // Checking the user input
        mUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mLoginButton.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Defining a listener for the main button
        mLoginButton.setOnClickListener((View.OnClickListener) this);
        mLoginButton.setTag(0);
    }

    @Override
    public void onClick(View v) {


        String pPseudo = mUsername.getText().toString();
        String pPassword = mPassword.getText().toString();
        String message = "Erreur";
        //boolean credentialsFound = false;
        credentialsFound = false;

        // We check first if the input is an existing pseudo
        if (checkIfElementExistsInList(existingPseudos, pPseudo)) {
            // For an existing pseudo, we verify the password
            String storedPassword = userDao.getPasswordByPseudo(pPseudo);
            if (verifyEntryAndHashValue(pPassword, storedPassword)) {
                credentialsFound = true;
                message = "Bienvenue";
            }
            else {
                //credentialsFound = false;
                message = "Mauvais mot de passe";
            }
        }
        else {
            prefPseudo = getPreferences(MODE_PRIVATE).getString(PREFERENCES_KEY_USERNAME, null);
            if (prefPseudo == pPseudo) {
                credentialsFound = true;
                message = "Bienvenue";
            }
            else {
                message = "Pseudo non reconnu";
            }
        }

        // Following the latest verifications
        if (credentialsFound) {
            // Saving the user's data as Preferences
            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            preferences.edit().putString(PREFERENCES_KEY_USERNAME, mUsername.getText().toString()).apply();
            preferences.edit().putString(PREFERENCES_KEY_PASSWORD, mPassword.getText().toString()).apply();
        }

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

        //new Handler().postDelayed(new Runnable() {
            //@Override
            //public void run() {
                if (!credentialsFound) {
                    initializeFields();
                }
                else {
                    // Access to the offers list
                    Intent offersListActivity = new Intent(LoginActivity.this, OffersListActivity.class);
                    //Bundle bundle = new Bundle();
                    //bundle.putString("userInput", userInput);
                    //offersListActivity.putExtras(bundle);
                    startActivity(offersListActivity);
                }
           // }
        //}, 20);

    }

    public void showPasswordResetForm(View v) {
        //Toast.makeText(getApplicationContext(), "Succès", Toast.LENGTH_LONG).show();
        Intent passwordResetActivity = new Intent(LoginActivity.this, PasswordResetActivity.class);
        startActivity(passwordResetActivity);
    }

    public void showSubscriptionForm(View v) {
        //Toast.makeText(getApplicationContext(), "Succès", Toast.LENGTH_LONG).show();
        Intent subscriptionActivity = new Intent(LoginActivity.this, SubscriptionActivity.class);
        startActivity(subscriptionActivity);
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
        mUsername.setText("");
        mPassword.setText("");
        mLoginButton.setEnabled(false);
    }

    public String hashStringUsingBCrypt(String entry) {
        String encryptedValue = BCrypt.withDefaults().hashToString(12, entry.toCharArray());

        return encryptedValue;
    }

    public boolean verifyEntryAndHashValue(String entry, String hashValue) {
        BCrypt.Result result = BCrypt.verifyer().verify(entry.toCharArray(), hashValue);

        return result.verified;
    }

    protected void retrieveAllExistingPseudos() {
        if(userDao.getNumberOfUsers() > 0) {
            existingPseudos = userDao.getDistinctPseudos();
        }
    }

    protected boolean checkIfElementExistsInList(List<String> myList, String myElement) {
        if (myList.contains(myElement.toLowerCase())) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        String password = "test2";

        String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        // $2a$12$US00g/uMhoSBm.HiuieBjeMtoN69SN.GE25fCpldebzkryUyopws6
        //Hash=$2a$12$LXyJ88tv2NUwsSMHJn4j.u4UQnttuAzCZt4OwC32Zy7AtX5KNiC8m
        //Hash=$2a$12$.2BawI5z26dF7Jw9CdzZxOKb7OCfpQfvJbF8n2ruYc16h06PGC7hC
        System.out.println("Password=" + password);
        System.out.println("Hash=" + bcryptHashString);

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), bcryptHashString);
        // result.verified == true
        System.out.println("Checking=" + result.verified);

        String secondHash = "$2a$12$.2BawI5z26dF7Jw9CdzZxOKb7OCfpQfvJbF8n2ruYc16h06PGC7hC";
        BCrypt.Result result1 = BCrypt.verifyer().verify(password.toCharArray(), secondHash);
        System.out.println("Checking2=" + result1.verified);

    }
}