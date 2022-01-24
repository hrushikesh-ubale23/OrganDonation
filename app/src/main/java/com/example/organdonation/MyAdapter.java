package com.example.organdonation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<donor> userArrayList;

    public MyAdapter(Context context, ArrayList<donor> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        donor donor=userArrayList.get(position);

        holder.name.setText(donor.DonorName);
        holder.phone.setText(donor.Phone);
        holder.bloodg.setText(donor.BloodGroup);
        holder.organ.setText(donor.Organs);

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,phone,bloodg,organ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.vfirstname);
            phone=itemView.findViewById(R.id.dnumber);
            bloodg=itemView.findViewById(R.id.bloodg);
            organ=itemView.findViewById(R.id.dorgan);
            //button.seton
        }
    }
}
