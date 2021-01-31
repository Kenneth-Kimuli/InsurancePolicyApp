package com.ipa.www.insuracepolicyapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordReset extends AppCompatActivity {

    private EditText Passwordemail;
    private Button Passwordreset;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        Passwordemail = (EditText)findViewById(R.id.etPasswordemail);
        Passwordreset = (Button)findViewById(R.id.btnPasswordreset);
        firebaseAuth = FirebaseAuth.getInstance();

        Passwordreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userpasswordemail = Passwordemail.getText().toString().trim();

                if(userpasswordemail == null){
                    Toast.makeText(PasswordReset.this, "Please ented the email you registered with", Toast.LENGTH_LONG).show();
                }else {
                    firebaseAuth.sendPasswordResetEmail(userpasswordemail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PasswordReset.this, "Password reset email sent!",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(PasswordReset.this, LogIn.class));
                            }else {
                                Toast.makeText(PasswordReset.this, "Error in sending the Password reset email!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
