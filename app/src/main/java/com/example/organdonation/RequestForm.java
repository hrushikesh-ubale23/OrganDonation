package com.example.organdonation;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RequestForm extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore mStore;
    private EditText adress,dob,gender,blood;
    private Button takepledge;
    private CheckBox kidney,liver,heart,intestine,pancrease,lung,checkBox;
    private String organ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_form);
        drawerLayout=findViewById(R.id.drawer_layoutd);
        navigationView=(NavigationView) findViewById(R.id.nav_viewd);
        toolbar=findViewById(R.id.toolbard);
        adress=findViewById(R.id.adressp);
        dob=findViewById(R.id.dobp);
        gender=findViewById(R.id.genderp);
        blood=findViewById(R.id.bloodp);
        kidney=findViewById(R.id.kidneyp);
        liver=findViewById(R.id.liverp);
        heart=findViewById(R.id.heartp);
        intestine=findViewById(R.id.intestinep);
        pancrease=findViewById(R.id.pancreasep);
        checkBox=findViewById(R.id.checkBoxp);
        lung=findViewById(R.id.lungp);
        takepledge=findViewById(R.id.submit);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        mStore = FirebaseFirestore.getInstance();
        DocumentReference d = mStore.collection("Patient").document(mUser.getUid());


        setSupportActionBar(toolbar);
        //hide
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_login).setVisible(false);
        menu.findItem(R.id.nav_OrganRequests).setVisible(false);
        menu.findItem(R.id.nav_availabledonors).setVisible(false);
        menu.findItem(R.id.nav_Pledge).setVisible(false);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_open,R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_pateintRequest);

        takepledge.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String adress1= adress.getText().toString();
                String dob1= dob.getText().toString();
                String gender1= gender.getText().toString();
                String bd1= blood.getText().toString();
                if(adress1.isEmpty()){
                    adress.setError("Enter the address");
                }else if(dob1.isEmpty()){
                    dob.setError("Enter date of birth");
                }else if(gender1.isEmpty()){
                    gender.setError("Enter Gender");
                }else if(bd1.isEmpty()){
                    blood.setError("Enter the date of birth");
                }
                else if(!lung.isChecked() &&!kidney.isChecked() && !liver.isChecked() && !pancrease.isChecked() && !intestine.isChecked() &&!heart.isChecked() )
                {
                    Toast.makeText(RequestForm.this,"Please select the organ requesting for",Toast.LENGTH_LONG).show();
                }
                else if(!checkBox.isChecked()){
                    Toast.makeText(RequestForm.this,"Please Agree",Toast.LENGTH_LONG).show();
                }
                else{

                    organ="";
                    if(lung.isChecked()){
                        organ=organ+lung.getText().toString()+" ";
                    }

                    if(heart.isChecked()){
                        organ=organ+heart.getText().toString()+" ";


                    }if(kidney.isChecked()){
                        organ=organ+kidney.getText().toString()+" ";
                    }if(liver.isChecked()){
                        organ=organ+liver.getText().toString()+" ";
                    }if(pancrease.isChecked()){
                        organ=organ+pancrease.getText().toString()+" ";
                    }if(intestine.isChecked()){
                        organ=organ+intestine.getText().toString()+" ";
                    }
                    d.update("Organs",organ);
                    d.update("Adress", adress1);
                    d.update("DOB", dob1);
                    d.update("BloodGroup", bd1);
                    d.update("Gender", gender1);

                    Toast.makeText(RequestForm.this,"Request successful",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RequestForm.this,PatientHome.class));
                    finish();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_login:
                Intent intent = new Intent(RequestForm.this,PatientLogin.class);
                startActivity(intent);
                break;

            case R.id.nav_pateintRequest:
                break;

            case R.id.nav_Progress:
                break;

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(RequestForm.this,"Logged out",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RequestForm.this,HomeActivity.class));
                finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
