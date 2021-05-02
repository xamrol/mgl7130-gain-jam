package com.example.jam.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.jam.R;
import com.example.jam.model.AppDatabase;
import com.example.jam.model.Job;
import com.example.jam.model.JobDao;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Integer Data[];
    Context mContext;

    // Database and Dao instantiations
    public AppDatabase db;
    public JobDao jobDao;

    //the parameters in this constructor depend on the elements
    // you want to show on the recycler adapter, in our case the job name etc ...
    public RecyclerAdapter(Integer[] data, Context context) {
        Data = data;
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    //these methods are needed for the adapter to work properly
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.job_offer_box, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        // Using the database
        db = Room.databaseBuilder(mContext.getApplicationContext(),
                AppDatabase.class, "dbjam.db")
                //.createFromAsset("database/dbjam.db")
                //.createFromAsset(getDatabasePath("dbjam.db").getAbsolutePath())
                //.fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                //.addMigrations(MIGRATION_1_2)
                .build();
        jobDao = db.JobDao();
        Job myJob = jobDao.getJobById(Data[position]);

        //holder.mTextView.setText(Data[position]);
        holder.mTextView.setText(myJob.getJobTitle());
        holder.mCompanyNameTextView.setText(myJob.getJobCompany());
        holder.mWorkingHoursTextView.setText(myJob.getJobWorkingHours());
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Clicked on" + Data[position], Toast.LENGTH_SHORT).show();
            }
        });
        holder.mDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Toast.makeText(mContext, "fonctionnalité a venir", Toast.LENGTH_SHORT).show();
                // We redirect the user to the job details page
                Intent jobDetailsActivity = new Intent(v.getContext(), JobDetailsActivity.class);
                jobDetailsActivity.putExtra("jobId", Data[position]);
                mContext.startActivity(jobDetailsActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Data.length;
    }


    //the inner class inside the RecyclerAdapter
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;
        TextView mCompanyNameTextView;
        TextView mWorkingHoursTextView;
        TextView mDetails;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.job_title);
            mCompanyNameTextView = itemView.findViewById(R.id.company_name);
            mWorkingHoursTextView = itemView.findViewById(R.id.working_hours);
            mDetails = itemView.findViewById(R.id.details);
        }
    }
}
