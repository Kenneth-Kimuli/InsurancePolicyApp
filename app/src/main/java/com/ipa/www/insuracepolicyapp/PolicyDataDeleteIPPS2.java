package com.ipa.www.insuracepolicyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PolicyDataDeleteIPPS2 extends AppCompatActivity {
    private EditText DName, DDescription, DPrice;
    private Button DDelete;
    private android.support.v7.widget.Toolbar toolbar;
    DatabaseReference databaseReference, policyRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy_data_delete);
        setUpUIViews();
        initializeToolbar();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        policyRef = databaseReference.child("Insurance Policies").child("-L98XIIYDCuXhcRAWi5H");

        policyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PolicyData policyData = dataSnapshot.getValue(PolicyData.class);
                DName.setText(policyData.getPolicyName());
                DDescription.setText(policyData.getPolicyDescription());
                DPrice.setText("Kshs. " + policyData.getPolicyPrice());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PolicyDataDeleteIPPS2.this, databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });

        DDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                policyRef.setValue(null);

                Toast.makeText(PolicyDataDeleteIPPS2.this, "Delete Successsful", Toast.LENGTH_SHORT);

                finish();

                startActivity(new Intent(PolicyDataDeleteIPPS2.this, ProvidersHome.class));
            }
        });
    }

    private void setUpUIViews(){
        DName = findViewById(R.id.etDName);
        DDescription = findViewById(R.id.etDDescription);
        DPrice = findViewById(R.id.etDPrice);
        DDelete = findViewById(R.id.btnDeletePolicyData);
        toolbar = findViewById(R.id.tbpdd);
    }

    private void initializeToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Policy Delete");
    }
}
