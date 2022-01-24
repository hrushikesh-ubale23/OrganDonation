package com.example.organdonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText profileFullName,profileEmail,profilePhone;
    FirebaseAuth fAuth;
    Button saveBtn;
    FirebaseFirestore fstore;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent data =getIntent();
        String fullName = data.getStringExtra("fullName");
        String email = data.getStringExtra("email");
        String phone = data.getStringExtra("phone");

        fAuth= FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();

        profileFullName=findViewById(R.id.nameEdit);
        profileEmail=findViewById(R.id.emailEdit);
        profilePhone=findViewById(R.id.phoneEdit);
        saveBtn=findViewById(R.id.saveEdit);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileEmail.getText().toString().isEmpty() || profilePhone.getText().toString().isEmpty() || profileFullName.getText().toString().isEmpty()){
                    Toast.makeText(EditProfile.this,"One or many fields are Empty.",Toast.LENGTH_SHORT).show();
                    return;
                }
                String email = profileEmail.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        DocumentReference docRef=fstore.collection("Doctor").document(user.getUid());
                        Map<String,Object> edited =new HashMap<>();
                        edited.put("Email",email);
                        edited.put("doctorName",profileFullName.getText().toString());
                        edited.put("Phone",profilePhone.getText().toString());
                        docRef.update(edited);
                        Toast.makeText(EditProfile.this,"Profile Updated",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),DoctorHome.class));
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        profileFullName.setText(fullName);
        profilePhone.setText(phone);
        profileEmail.setText(email);

        Log.d(TAG,"onCreate: "+ fullName+" "+email+" "+phone);
    }
}