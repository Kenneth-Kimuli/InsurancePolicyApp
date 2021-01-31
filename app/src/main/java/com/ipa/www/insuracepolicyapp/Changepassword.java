package com.ipa.www.insuracepolicyapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Changepassword extends AppCompatActivity {

    private EditText Newpassword;
    private Button Updatepassword;
    private FirebaseUser firebaseUser;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        setUpUIViews();
        initializeToolbar();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Updatepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Passwordnew = Newpassword.getText().toString();
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                firebaseUser.updatePassword(Passwordnew).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Changepassword.this, "Password has been changed!", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(Changepassword.this, "Password update failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    private void setUpUIViews(){
        Newpassword = findViewById(R.id.etNewpassword);
        Updatepassword = findViewById(R.id.btnChangepassword);
        toolbar = findViewById(R.id.tbChangePassword);
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
        getSupportActionBar().setTitle("InsuranceApp");
    }
}
