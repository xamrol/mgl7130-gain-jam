package com.example.jam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    String Data[];
    Context mContext;

    //the parameters in this constructor depend on the elements
    // you want to show on the recycler adapter, in our case the job name etc ...
    public RecyclerAdapter(String[] data, Context context) {
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
        holder.mTextView.setText(Data[position]);
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Clicked on" + Data[position], Toast.LENGTH_SHORT).show();
            }
        });
        holder.mDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Toast.makeText(mContext, "fonctionnalité a venir", Toast.LENGTH_SHORT).show();
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
        TextView mDetails;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.job_title);
            mDetails = itemView.findViewById(R.id.details);
        }
    }
}
