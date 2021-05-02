package com.example.jam.controller;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.jam.R;

public class PasswordResetCodeActivity extends AppCompatActivity implements View.OnClickListener {

    /*
      Declarations & Initializations
    */
    private EditText mPageInput;
    private Button mPageButton;
    private TextView mPageInfo;

    // Other useful variables
    private String mPseudo;
    private String mGeneratedCode;
    private String message;
    private boolean entryAccepted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset_code);

        // Retrieving the interface's components
        mPageInfo = (TextView) findViewById(R.id.reset_page_info_txt);
        mPageInput = (EditText) findViewById(R.id.reset_page_main_input);
        mPageButton = (Button) findViewById(R.id.reset_page_main_btn);

        // Beginning
        mPageButton.setEnabled(false);
        mPageButton.setText("Continuer");
        mPageInput.setHint("Valeur");
        mGeneratedCode = generateRandomDigits();    // Random code generation
        //mPageInfo.setText("Saisissez le code suivant : " + mGeneratedCode);
        mPageInfo.setText("Entrez une valeur pour montrer que vous n'êtes pas un robot");
        //mPageInput.setText(mGeneratedCode);

        // Retrieving a previous activity extras
        Intent pResetCodeActivity = getIntent();
        if (pResetCodeActivity != null) {
            if (pResetCodeActivity.hasExtra("userInput")) {
                mPseudo = pResetCodeActivity.getStringExtra("userInput");
                //Toast.makeText(this, mPseudo, Toast.LENGTH_SHORT).show();
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
        //String userInput = mPageInput.getText().toString();

        message = "Succès";
        entryAccepted = true;

        /*
        if (userInput == mGeneratedCode) {
            message = "Succès";
            entryAccepted = true;
        }
        else {
            message = "Mauvais code";
            entryAccepted = false;
            mPageInput.setText("");
        }
        */

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (entryAccepted) {
                    // We redirect the user to the login page
                    Intent pResetFinalActivity = new Intent(PasswordResetCodeActivity.this, PasswordResetFinalActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("userGivenPseudo", mPseudo);
                    pResetFinalActivity.putExtras(bundle);
                    startActivity(pResetFinalActivity);
                }
            }
        }, 2000);

    }

    protected String generateRandomDigits() {
        //String result = "" + ((int) (Math.random() * 9000) + 1000);
        int randomValue = (int) (Math.random() * 9000) + 1000;
        String result = Integer.toString(randomValue);
        /*
        Random rand = new Random();
        int randNumber = rand.nextInt((9999 - 100) + 1) + 10;
        result = Integer.toString(randNumber);
        */
        return result;
    }

    protected String retrieveGivenCode() {

        String lInput = mPageInfo.getText().toString();
        int lInputLength = lInput.length();
        String result = lInput.substring(lInputLength - 5, lInputLength).trim();

        return result;
    }

}
