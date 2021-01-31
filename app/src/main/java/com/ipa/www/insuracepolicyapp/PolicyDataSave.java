package com.ipa.www.insuracepolicyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PolicyDataSave extends AppCompatActivity {

    private TextView PolicyName, PolicyDescription, PolicyPrice;
    private EditText PName, PDescripton, Pprice, ProvName;
    private Button PSave, PEdit, PDelete;
    private DatabaseReference databaseReference, policyRef;
    String PNamestr, PDescriptonstr, Ppricestr, PProvNamestr;
    private android.support.v7.widget.Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy_data_save);
        setUpUIViews();
        initializeToolbar();

        PSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePolicyData();
                Toast.makeText(PolicyDataSave.this, "Upload succesful!", Toast.LENGTH_SHORT).show();
            }
        });

        PEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PolicyDataSave.this, PolicyDataUpdateIPPS1.class));
            }
        });

        PDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PolicyDataSave.this, PolicyDataDeleteIPPS1.class));
            }
        });
    }

    private void setUpUIViews(){
        PName = findViewById(R.id.etPolicyName);
        PDescripton = findViewById(R.id.etPolicyDescription);
        Pprice = findViewById(R.id.etPolicyPrice);
        PSave = findViewById(R.id.btnSavePolicyData);
        ProvName = findViewById(R.id.etPolicyProviderName);
        PEdit = findViewById(R.id.btnEditPolicyData);
        PDelete = findViewById(R.id.btnDeletePolicyDetails);
        toolbar = findViewById(R.id.tbpds);
    }

    private void savePolicyData(){
        PNamestr = PName.getText().toString();
        PDescriptonstr = PDescripton.getText().toString();
        Ppricestr = Pprice.getText().toString();
        PProvNamestr = ProvName.getText().toString();

        //database reference pointing to root of database
        databaseReference = FirebaseDatabase.getInstance().getReference();
        //database reference pointing to child node
        policyRef = databaseReference.child("Insurance Policies");
        PolicyData policyData = new PolicyData(PNamestr, PProvNamestr, PDescriptonstr, Ppricestr);
        policyRef.push().setValue(policyData);
    }
    private void initializeToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Policy Details");
    }
}
