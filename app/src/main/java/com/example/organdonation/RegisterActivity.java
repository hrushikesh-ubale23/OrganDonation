package com.example.organdonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private TextView alreadyHaveAccount;
    private EditText inputEmail,inputPassword,confirmPassword,name,phone;
    private Button signup;
    private String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore mStore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        alreadyHaveAccount=findViewById(R.id.alreadyHaveAccount);

        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        confirmPassword=findViewById(R.id.confirmPassword);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        signup=findViewById(R.id.signup);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        mStore = FirebaseFirestore.getInstance();

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, DoctorLogin.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerforAuth();
            }

            private void PerforAuth() {
                String email= inputEmail.getText().toString();
                String person= name.getText().toString();
                String phoneNo= phone.getText().toString();
                String password= inputPassword.getText().toString();
                String confirmpassword=confirmPassword.getText().toString();

                if (!email.matches(emailPattern)){
                    inputEmail.setError("Enter correct email");
                }else if (password.isEmpty() || password.length()<6){
                    inputPassword.setError("Enter Password whose length greater than 6");
                }else if (!password.equals(confirmpassword)){
                    confirmPassword.setError("Your password does not match");
                }else
                {
                    progressDialog.setMessage("Please Wait while Registration....");
                    progressDialog.setTitle("Registration");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                DocumentReference df = mStore.collection("Doctor").document(mAuth.getCurrentUser().getUid());
                                Map<String,Object> doctor = new HashMap<>();
                                doctor.put("Email", email);
                                doctor.put("doctor Name",person);
                                doctor.put("Phone", phoneNo);
                                df.set(doctor).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(RegisterActivity.this,"Registration successful",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                progressDialog.dismiss();
                                sendUserToNewActivity();

                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this,"error"+ task.getException(),Toast.LENGTH_SHORT).show();
                            }
                        }

                        private void sendUserToNewActivity() {
                            Intent intent= new Intent(RegisterActivity.this,DoctorHome.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
                }

            }

        });

    }
}