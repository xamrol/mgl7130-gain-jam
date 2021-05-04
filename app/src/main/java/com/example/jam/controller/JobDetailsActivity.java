package com.example.jam.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jam.R;
import com.example.jam.model.AppDatabase;
import com.example.jam.model.Job;
import com.example.jam.model.JobDao;
import com.google.android.material.navigation.NavigationView;

public class JobDetailsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private DrawerLayout mDrawerLayout2;
    NavigationView mNavigationView2;
    Toolbar mToolbar2 = null;

    private TextView mJobTitle;
    private TextView mCompanyName;
    private TextView mWorkingHours;
    private Button mReplyOfferBtn;

    // Database and Dao instantiations
    public AppDatabase db;
    public JobDao jobDao;

    private int jobId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        mJobTitle = (TextView) findViewById(R.id.job_title);
        mCompanyName = (TextView) findViewById(R.id.company_name);
        mWorkingHours = (TextView) findViewById(R.id.working_hours);
        mReplyOfferBtn = (Button) findViewById(R.id.reply_offer_btn);

        mToolbar2 = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar2);
        mDrawerLayout2 = findViewById(R.id.drawLayout);
        mNavigationView2 = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView2.setNavigationItemSelectedListener(this);
        mNavigationView2.setCheckedItem(R.id.liste);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout2, mToolbar2,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout2.addDrawerListener(toggle);
        //this method will take care of the rotating hamburger icon when you open the drawer
        toggle.syncState();

        // Retrieving a previous activity extras
        Intent jobDetailsActivity = getIntent();
        if (jobDetailsActivity != null) {
            jobId = 0;
            if (jobDetailsActivity.hasExtra("jobId")) {
                jobId = jobDetailsActivity.getIntExtra("jobId", 0);
            }
        }

        // Using the database
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "dbjam.db")
                //.createFromAsset("database/dbjam.db")
                //.createFromAsset(getDatabasePath("dbjam.db").getAbsolutePath())
                //.fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                //.addMigrations(MIGRATION_1_2)
                .build();
        jobDao = db.JobDao();
        Job myJob = jobDao.getJobById(jobId);

        // Initializations
        mJobTitle.setText(myJob.getJobTitle());
        mCompanyName.setText(myJob.getJobCompany() +" (Employeur)");
        mWorkingHours.setText(myJob.getJobWorkingHours() + " par semaine");

        // Array data
        final String [] col1 = {"Catégorie","Contact","Statut","Type Emploi","Exigence1","Exigence2","Exigence3"};

        final String [] col2 = {myJob.getJobCategory(),myJob.getJobContact(),
                "Actif",myJob.getJobPreferences(),myJob.getJobRequirement1(),
                myJob.getJobRequirement2(),myJob.getJobRequirement3()};

        TableLayout table = (TableLayout) findViewById(R.id.idTable); // on prend le tableau défini dans le layout
        TableRow row; // création d'un élément : ligne
        TextView tv1,tv2; // création des cellules
        Button mainBtn;

        // pour chaque ligne
        for(int i=0;i<col1.length;i++) {
            row = new TableRow(this); // création d'une nouvelle ligne

            tv1 = new TextView(this); // création cellule
            tv1.setText(col1[i]); // ajout du texte
            tv1.setGravity(Gravity.LEFT); // centrage dans la cellule
            tv1.setTextSize(14);
            // adaptation de la largeur de colonne à l'écran :
            tv1.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );

            // idem 2ème cellule
            tv2 = new TextView(this);
            tv2.setText(col2[i]);
            tv2.setGravity(Gravity.RIGHT);
            tv2.setTextSize(14);
            tv2.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );

            // ajout des cellules à la ligne
            row.addView(tv1);
            row.addView(tv2);
            row.setPadding(10, 25, 10, 25);

            // ajout de la ligne au tableau
            table.addView(row);
        }

        // Adding the button
        /*
        row = new TableRow(this);
        mainBtn = new Button(this);
        mainBtn.setGravity(Gravity.CENTER);
        //mainBtn.setBackground(Drawable.createFromXml());
        mainBtn.setText("Postuler");
        //mainBtn.setTextColor(Integer.parseInt("#090909"));
        row.addView(mainBtn);
        table.addView(row);
        */

        mReplyOfferBtn.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(), "Succès", Toast.LENGTH_LONG).show();
        //sendEmail();
    }

    @Override
    public void onBackPressed(){
        if (mDrawerLayout2.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout2.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id2 = item.getItemId();
        switch (id2){
            case R.id.profil:
                Intent p2 = new Intent(JobDetailsActivity.this, ProfileActivity.class);
                startActivity(p2);
                break;
            case R.id.carte:
                Intent c3 = new Intent(JobDetailsActivity.this, JobsMapActivity.class);
                startActivity(c3);
                break;
            case R.id.liste:
                Intent h2 = new Intent(JobDetailsActivity.this, OffersListActivity.class);
                startActivity(h2);
                break;
        }
        mDrawerLayout2.closeDrawer(GravityCompat.START);
        return true;
    }

    // This function starts a new mail intent and gives a user the opportunity to choose the mailing app
    @SuppressLint("LongLogTag")
    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {"g.senami94@gmail.com"};
        String[] CC = {"g.senami94@outlook.fr"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[ANDROID] - A sending mail test");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "This is just a test");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
            Toast.makeText(getApplicationContext(), "Succès", Toast.LENGTH_LONG).show();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(JobDetailsActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    protected void sendMail(String email, String subject, String messageBody) {
        String jobNo;
        String teamNo;
        final String username = "abc@gmail.com";
        final String password = "000000";
        final String emailid = "xyz@outlook.com";
        //final String subject = "Photo";
        final String message = "Hello";

        Intent intent = getIntent();

    }

}