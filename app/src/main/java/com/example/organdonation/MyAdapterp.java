package com.example.organdonation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterp extends RecyclerView.Adapter<MyAdapterp.MyViewHolderp> {

    Context context;
    ArrayList<Patient> patientArrayList;

    public MyAdapterp(Context context, ArrayList<Patient> patientArrayList) {
        this.context = context;
        this.patientArrayList = patientArrayList;
    }

    @NonNull
    @Override
    public MyAdapterp.MyViewHolderp onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.itemp,parent,false);

        return new MyViewHolderp(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterp.MyViewHolderp holder, int position) {

        Patient patient =patientArrayList.get(position);
        holder.patientName.setText(patient.PatientName);
        holder.contact.setText(patient.Phone);
        holder.bloodGroup.setText(patient.BloodGroup);
        holder.organ.setText(patient.Organs);

    }

    @Override
    public int getItemCount() {
        return patientArrayList.size();
    }

    public static class MyViewHolderp extends  RecyclerView.ViewHolder{

        TextView patientName,contact,bloodGroup,organ;


        public MyViewHolderp(@NonNull View itemView) {
            super(itemView);

            patientName= itemView.findViewById(R.id.pfirstname);
            contact=itemView.findViewById(R.id.pnumber);
            bloodGroup=itemView.findViewById(R.id.bloodp);
            organ=itemView.findViewById(R.id.porgan);

        }
    }
}
