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

public class DonorHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Button takepledge;
    Button logout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_home);
        /*takepledge=findViewById(R.id.donor_pledge);
        logout=findViewById(R.id.backbtn);
        logout.setOnClickListener((v) ->{
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(DonorHome.this,"Logged out",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(DonorHome.this,HomeActivity.class));
        });
        takepledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DonorHome.this,PledgeForm.class);
                startActivity(intent);
            }
        });*/
        drawerLayout=findViewById(R.id.drawer_layoutd);
        navigationView=(NavigationView) findViewById(R.id.nav_viewd);
        toolbar=findViewById(R.id.toolbard);

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

            case R.id.nav_profile:
                break;

            case R.id.nav_Pledge:
                Intent intent=new Intent(DonorHome.this,PledgeForm.class);
                startActivity(intent);
                break;

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(DonorHome.this,"Logged out",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DonorHome.this,HomeActivity.class));
                finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}