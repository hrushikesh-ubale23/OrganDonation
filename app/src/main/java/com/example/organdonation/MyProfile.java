package com.example.organdonation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MyProfile extends AppCompatActivity {
    TextView fullName,email,phone;
    FirebaseAuth fAuth;
    Button changeProfile;
    FirebaseFirestore fstore;
    String userId;
    FirebaseUser user;
    ImageView profileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        phone=findViewById(R.id.profileNumber);
        fullName=findViewById(R.id.profileName);
        email=findViewById(R.id.profileEmail);
//        profileImage=findViewById(R.id.profileImage);
        changeProfile=findViewById(R.id.editProfile);
        fAuth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        userId =fAuth.getCurrentUser().getUid();
        final FirebaseUser user = fAuth.getCurrentUser();

        DocumentReference documentReference =fstore.collection("Doctor").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    phone.setText(value.getString("Phone"));
                    fullName.setText(value.getString("doctorName"));
                    email.setText(value.getString("Email"));
                }
            }
        });
//        profileImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Open Gallery
//                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(openGalleryIntent,1000);
//
//            }
//        });
        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),EditProfile.class);
                i.putExtra("fullName","Hrushikesh Ubale");
                i.putExtra("email","ubalehrushikesh18@gmail.com");
                i.putExtra("phone","8657370013");
                startActivity(i);
            }
        });

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==1000){
//            if (resultCode == Activity.RESULT_OK){
//                Uri imageUri = data.getData();
//                profileImage.setImageURI(imageUri);
//            }
//        }
//
//    }
}
