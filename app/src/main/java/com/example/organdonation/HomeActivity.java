package com.example.organdonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    private LinearLayout doctor;
    private LinearLayout patient;
    private LinearLayout donor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        doctor=findViewById(R.id.doctor_layout);
        patient=findViewById(R.id.patient_layout);
        donor=findViewById(R.id.donor_layout);

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenLoginActivity();
            }

        });

        donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,DonorLogin.class);
                startActivity(intent);
            }
        });
        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,PatientLogin.class);
                startActivity(intent);
            }
        });
    }
    public void OpenLoginActivity(){
        Intent intent = new Intent(this, DoctorLogin.class);
        startActivity(intent);
    }

   /* @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            startActivity(new Intent(HomeActivity.this,DoctorHome.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
    } */
}

