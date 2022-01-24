package com.example.organdonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class PatientHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Button logout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        toolbar= findViewById(R.id.toolbar);



    //    logout.setOnClickListener((v) ->{
    //        FirebaseAuth.getInstance().signOut();
    //        Toast.makeText(PatientHome.this,"Logged out",Toast.LENGTH_SHORT).show();
    //        startActivity(new Intent(PatientHome.this,HomeActivity.class));
    //    });



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

        navigationView.setCheckedItem(R.id.nav_home);
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
                Intent intent = new Intent(PatientHome.this,PatientLogin.class);
                startActivity(intent);
                break;

            case R.id.nav_pateintRequest:
                Intent intent1 = new Intent(PatientHome.this,RequestForm.class);
                startActivity(intent1);
                 break;

            case R.id.nav_profile:
                Intent intent2 = new Intent(PatientHome.this,MyProfilep.class);
                startActivity(intent2);
                break;

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(PatientHome.this,"Logged out",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PatientHome.this,HomeActivity.class));
                finish();

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}