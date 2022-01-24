package com.example.organdonation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class DoctorHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<donor> userArrayList;
    MyAdapter myAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);
        drawerLayout=findViewById(R.id.drawer_layoutdd);
        navigationView=findViewById(R.id.nav_viewdd);
        toolbar=findViewById(R.id.toolbardd);

        progressDialog =new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data....");
        progressDialog.show();

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<donor>();
        myAdapter = new MyAdapter(DoctorHome.this,userArrayList);

        recyclerView.setAdapter(myAdapter);

        EventChangeListener();


        setSupportActionBar(toolbar);
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_login).setVisible(false);
        menu.findItem(R.id.nav_Pledge).setVisible(false);
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

    private void EventChangeListener() {

        db.collection("Donor").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

              if(error != null){
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                  Log.e("Firestore error",error.getMessage());
                  return;
              }
                for(DocumentChange dc : value.getDocumentChanges()){

                    if(dc.getType()==DocumentChange.Type.ADDED ){

                        userArrayList.add(dc.getDocument().toObject(donor.class));
                    }
                    myAdapter.notifyDataSetChanged();
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();

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

            case R.id.nav_profile:
                Intent intent2 = new Intent(DoctorHome.this,MyProfile.class);
                startActivity(intent2);
                break;

            case R.id.nav_availabledonors:
                Intent intent = new Intent(DoctorHome.this,DoctorHome.class);
                startActivity(intent);
                break;

            case R.id.nav_OrganRequests:
                Intent intent1 = new Intent(DoctorHome.this,PatientRequest.class);
                startActivity(intent1);
                break;

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(DoctorHome.this,"Logged out",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DoctorHome.this,HomeActivity.class));
                finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}