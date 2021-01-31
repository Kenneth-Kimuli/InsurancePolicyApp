package com.ipa.www.insuracepolicyapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private Button LogIn;
    private TextView SignUp, ForgotPassword;
    private FirebaseAuth firebaseAuth;
    //since verification may take a while, we can display a message to user
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Email = (EditText)findViewById(R.id.etEmail);
        Password = (EditText)findViewById(R.id.etPassword);
        LogIn = (Button)findViewById(R.id.btnLogIn);
        SignUp = (TextView)findViewById(R.id.tvSignUp);
        ForgotPassword = (TextView)findViewById(R.id.tvForgotPassword);

        firebaseAuth = FirebaseAuth.getInstance();

        //object of main class,
        FirebaseUser user = firebaseAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);

        //checks with db if user has already logged in, must direct him to next activity without asking him to login again
        if (user != null){
            finish();
            startActivity(new Intent(LogIn.this, Home.class));
        }

        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this, SignUp.class);
                startActivity(intent);
            }
        });

        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogIn.this, PasswordReset.class));
            }
        });
    }

    private void validate() {
        String emailstr = Email.getText().toString();
        String passwordstr = Password.getText().toString();

        //message to user on what's going on; clue
        progressDialog.setMessage("This might take a while...");
        progressDialog.show();

        if (emailstr.isEmpty()) {
            Toast.makeText(this, "Please enter a Name", Toast.LENGTH_SHORT).show();
        } else if (passwordstr.isEmpty()) {
            Toast.makeText(this, "Please enter a Password", Toast.LENGTH_SHORT).show();
        } else if (emailstr.equals("ippsprovider1@gmail.com")) {
            startActivity(new Intent(LogIn.this, ProvidersHome.class));
            Toast.makeText(this, "Welcome Insurance provider", Toast.LENGTH_SHORT).show();
        } else if (emailstr.equals("ippsprovider2@gmail.com")) {
            startActivity(new Intent(LogIn.this, ProvidersHome.class));
            Toast.makeText(this, "Welcome Insurance provider", Toast.LENGTH_SHORT).show();
        } else {

            firebaseAuth.signInWithEmailAndPassword(emailstr, passwordstr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        //startActivity(new Intent(LogIn.this, Home.class));
                        //Toast.makeText(LogIn.this, "LogIn Successful", Toast.LENGTH_SHORT).show();
                        checkEmailVerification();
                    } else {
                        Toast.makeText(LogIn.this, "LogIn Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailverify = firebaseUser.isEmailVerified();

        if (emailverify){
            finish();
            startActivity(new Intent(LogIn.this, Home.class));
        }else {
            Toast.makeText(this, "Verify your email",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

}
