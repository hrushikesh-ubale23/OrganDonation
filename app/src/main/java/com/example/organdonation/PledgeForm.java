package com.example.organdonation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class PledgeForm extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
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
        setContentView(R.layout.activity_pledge_form);
        drawerLayout=findViewById(R.id.drawer_layoutd);
        navigationView=(NavigationView) findViewById(R.id.nav_viewd);
        toolbar=findViewById(R.id.toolbard);
        adress=findViewById(R.id.adress);
        dob=findViewById(R.id.dob);
        gender=findViewById(R.id.gender);
        blood=findViewById(R.id.blood);
        kidney=findViewById(R.id.kidney);
        liver=findViewById(R.id.liver);
        heart=findViewById(R.id.heart);
        intestine=findViewById(R.id.intestine);
        pancrease=findViewById(R.id.pancrease);
        checkBox=findViewById(R.id.checkBox);
        lung=findViewById(R.id.lung);
        takepledge=findViewById(R.id.takePledge);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        mStore = FirebaseFirestore.getInstance();
        DocumentReference d = mStore.collection("Donor").document(mUser.getUid());


        setSupportActionBar(toolbar);
        //hide
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_login).setVisible(false);
        menu.findItem(R.id.nav_OrganRequests).setVisible(false);
        menu.findItem(R.id.nav_availabledonors).setVisible(false);
        menu.findItem(R.id.nav_pateintRequest).setVisible(false);
        menu.findItem(R.id.nav_Progress).setVisible(false);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_open,R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_Pledge);

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
                    Toast.makeText(PledgeForm.this,"Please select the organ to donate",Toast.LENGTH_LONG).show();
                }
                else if(!checkBox.isChecked()){
                    Toast.makeText(PledgeForm.this,"Please Agree",Toast.LENGTH_LONG).show();
                }
                else{

                    organ="";
                    if(lung.isChecked()){
                       organ=organ+lung.getText().toString()+" ";
                    }

                    if(heart.isChecked()){
                        organ=organ+heart.getText().toString()+" ";
//                        if(!organ.equals(null)){
//                            organ=organ+" "+heart.getText().toString();
//                        }else{
//                            organ=lung.getText().toString();
//                        }


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

                    Toast.makeText(PledgeForm.this,"Pledge successful",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PledgeForm.this,DonorHome.class));
                    finish();
//                    d.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                        @Override
//                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//
//                        }
//                    });
//                    Map<String,Object> donor = new HashMap<>();
//                    donor.putIfAbsent("Adress", adress1);
//                    donor.putIfAbsent("DOB", dob1);
//                    donor.putIfAbsent("Gender",gender1);
//                    donor.putIfAbsent("Blood Group",bd1);
//
//                    d.set(donor).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//                            Toast.makeText(PledgeForm.this,"Pledge successful",Toast.LENGTH_SHORT).show();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(getApplicationContext(), "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
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
                Intent intent=new Intent(PledgeForm.this,DonorHome.class);
                startActivity(intent);
                break;

            case R.id.nav_profile:
                break;

            case R.id.nav_Pledge:
                //   Intent intent=new Intent(DonorHome.this,PledgeForm.class);
                //  startActivity(intent);
                break;

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(PledgeForm.this,"Logged out",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PledgeForm.this,HomeActivity.class));
                finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
