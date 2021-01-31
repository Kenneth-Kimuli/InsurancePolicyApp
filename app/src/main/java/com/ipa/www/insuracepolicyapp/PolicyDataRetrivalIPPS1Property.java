package com.ipa.www.insuracepolicyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PolicyDataRetrivalIPPS1Property extends AppCompatActivity {

    private TextView RPolicyName, RPolicyDescription, RPolicyPrice;
    private DatabaseReference databaseReference, policyRef;
    private android.support.v7.widget.Toolbar toolbar;
    private Button Checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy_data_retrival);
        setUpUIViews();
        initializeToolbar();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        policyRef = databaseReference.child("Insurance Policies").child("-L98VFaE1qjNf5buUgfo");

        policyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PolicyData policyData = dataSnapshot.getValue(PolicyData.class);
                RPolicyName.setText(policyData.getPolicyName());
                RPolicyDescription.setText(policyData.getPolicyDescription());
                RPolicyPrice.setText("Kshs. " + policyData.getPolicyPrice());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PolicyDataRetrivalIPPS1Property.this, databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });

        Checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PolicyDataRetrivalIPPS1Property.this, Checkout.class));
            }
        });
    }

    private void setUpUIViews(){
        RPolicyName = findViewById(R.id.tvRName);
        RPolicyDescription = findViewById(R.id.tvRDescription);
        RPolicyPrice = findViewById(R.id.tvRPrice);
        toolbar = findViewById(R.id.tbpdr);
        Checkout = findViewById(R.id.btnCheckout);
    }
    private void initializeToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Policy Details");
    }
}
