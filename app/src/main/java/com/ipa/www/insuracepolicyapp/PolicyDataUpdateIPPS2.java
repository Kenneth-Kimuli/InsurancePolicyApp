package com.ipa.www.insuracepolicyapp;

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

public class PolicyDataUpdateIPPS2 extends AppCompatActivity {
    private EditText UName, UDescription, UPrice;
    private Button UUpdate;
    private android.support.v7.widget.Toolbar toolbar;
    DatabaseReference databaseReference, policyRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy_data_update);
        setUpUIViews();
        initializeToolbar();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        policyRef = databaseReference.child("Insurance Policies").child("-L98XIIYDCuXhcRAWi5H");

        policyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PolicyData policyData = dataSnapshot.getValue(PolicyData.class);
                UName.setText(policyData.getPolicyName());
                UDescription.setText(policyData.getPolicyDescription());
                UPrice.setText("Kshs. " + policyData.getPolicyPrice());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PolicyDataUpdateIPPS2.this, databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });

        UUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String PNamestr = UName.getText().toString();
                String PDescriptonstr = UDescription.getText().toString();
                String Ppricestr = UPrice.getText().toString();

                PolicyData policyData = new PolicyData(PNamestr, null, PDescriptonstr, Ppricestr);

                policyRef.setValue(policyData);

                finish();
            }
        });
    }

    private void setUpUIViews(){
        UName = findViewById(R.id.etUName);
        UDescription = findViewById(R.id.etUDescription);
        UPrice = findViewById(R.id.etUPrice);
        UUpdate = findViewById(R.id.btnUpdatePolicyData);
        toolbar = findViewById(R.id.tbpdu);
    }

    private void initializeToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Policy Update");
    }
}
