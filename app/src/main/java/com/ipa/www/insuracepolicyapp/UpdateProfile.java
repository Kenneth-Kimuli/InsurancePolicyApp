package com.ipa.www.insuracepolicyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfile extends AppCompatActivity {

    private EditText newName, newEmail, newIDnumber, newPhonenumber;
    private Button save;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, myRef;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        setUpUIViews();
        initializeToolbar();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

       // final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference = FirebaseDatabase.getInstance().getReference();
        myRef = databaseReference.child("Users").child(firebaseAuth.getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                newName.setText(userProfile.getName());
                newEmail.setText(userProfile.getEmail());
                newPhonenumber.setText(userProfile.getPhoneNumber());
                newIDnumber.setText(userProfile.getiDNumber());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UpdateProfile.this, databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Nname = newName.getText().toString();
                String Nemail = newEmail.getText().toString();
                String NIDnumber = newIDnumber.getText().toString();
                String NPhonenumber = newPhonenumber.getText().toString();

                UserProfile userProfile = new UserProfile(Nname, Nemail, NPhonenumber, NIDnumber);

                myRef.setValue(userProfile);

                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpUIViews(){
        newName = findViewById(R.id.etNameUpdate);
        newEmail = findViewById(R.id.etEmailUpdate);
        newIDnumber = findViewById(R.id.etIDNumberUpdate);
        newPhonenumber = findViewById(R.id.etPhonenumberUpdate);
        save = findViewById(R.id.btnSave);
        toolbar = findViewById(R.id.tbUpdateProfile);
    }

    private void initializeToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Profile");
    }
}
