package com.ipa.www.insuracepolicyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private ImageView ProfPic;
    private TextView ProfName, ProfEmail, ProfPhoneNumber, ProfIDNumber;
    private Button ProfUpdate, Changepassword;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setUppUIViews();
        initializeToolbar();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = databaseReference.child("Users").child(firebaseAuth.getUid());


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                ProfName.setText("Name: " + userProfile.getName());
                ProfEmail.setText("Email: " + userProfile.getEmail());
                ProfPhoneNumber.setText("Phone Number: " + userProfile.getPhoneNumber());
                ProfIDNumber.setText("ID Number: " + userProfile.getiDNumber());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Profile.this, databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });

        ProfUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this,UpdateProfile.class));
            }
        });

        Changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, Changepassword.class));
            }
        });
    }

    private void setUppUIViews(){
        ProfPic = findViewById(R.id.ivProfPic);
        ProfName = findViewById(R.id.tvProfName);
        ProfEmail = findViewById(R.id.tvProfEmail);
        ProfPhoneNumber = findViewById(R.id.tvProfPhoneNumber);
        ProfIDNumber = findViewById(R.id.tvProfIDNumber);
        ProfUpdate = findViewById(R.id.btnProfUpdate);
        Changepassword = findViewById(R.id.btnChangepassword);
        toolbar = findViewById(R.id.tbProfile);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
    }
}
