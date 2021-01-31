package com.ipa.www.insuracepolicyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button Logout;
    private android.support.v7.widget.Toolbar toolbar;
    private ImageView Property, Vehicle, Life, Health;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setUpUIviews();
        initializeToolbar();

        firebaseAuth = FirebaseAuth.getInstance();

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        Property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, InsuranceProviders.class));
            }
        });

        Vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, InsuranceProvidersVehicle.class));
            }
        });

        Life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, InsuranceProviders.class));
            }
        });

        Health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, InsuranceProviders.class));
            }
        });
    }

    private void setUpUIviews(){
        Logout = (Button)findViewById(R.id.btnLogout);
        toolbar = findViewById(R.id.tbmain);
        Property = findViewById(R.id.ivHomeProperty);
        Vehicle = findViewById(R.id.ivHomeVehicle);
        Life = findViewById(R.id.ivHomeLife);
        Health = findViewById(R.id.ivHomeHealth);
    }

    private void logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(Home.this, LogIn.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.Logoutmenu: {
                logout();
            }
            case R.id.Profilemenu: {
                startActivity(new Intent(Home.this, Profile.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }
    private void initializeToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("InsuranceApp");
    }
}
