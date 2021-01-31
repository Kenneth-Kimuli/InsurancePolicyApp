package com.ipa.www.insuracepolicyapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class SignUp extends AppCompatActivity {

    private EditText Username, Useremail, Userpassword, Confirmpassword, PhoneNumber, IDNumber;
    private Button Signup;
    private TextView Login;
    private ImageView Userpic;
    private FirebaseAuth firebaseAuth;
    String namestr, emailstr, passwordstr, confirmpasswordstr, PhoneNumberstr, IDNumberstr;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference; //storageReference is the main root file
    private static int PICK_IMAGE = 123;
    Uri imagePath; //unique resource identifier
    private android.support.v7.widget.Toolbar toolbar;
    private DatabaseReference myRef, databaseReference;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  //resultCode: checks if functionality implemented has executed successfully
        if (resultCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try { //since it cannot draw n IO Exception
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath); //convert image into a form in which the Imageview can process
                Userpic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }//elseif
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setupUIViews();
        initializeToolbar();


        //object of main class firebaseAuth, instance of authenticator
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        //storageReference = firebaseStorage.getInstance().getReference();
        //StorageReference myRef = storageReference.child(firebaseAuth.getUid()).getRoot();

        /*Userpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*"); //for docs; application/* ,for audio; audio/* (type of data being sent)
                intent.setAction(Intent.ACTION_GET_CONTENT); //predefined actions, e.g battery low
                startActivityForResult(Intent.createChooser(intent,"Select image"), PICK_IMAGE); //function takes the request code, if request is successful the intent executed
            }
        });*/



        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    //upload to database, trim removes the whitespaces
                    String user_email = Useremail.getText().toString().trim();
                    String user_password = Userpassword.getText().toString().trim();

                    //autocomplete;
                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                sendEmailVerification();
                            }else{
                                Toast.makeText(SignUp.this, "User Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, LogIn.class));
            }
        });
    }
    private void setupUIViews(){
        Username = (EditText)findViewById(R.id.etUsername);
        Useremail = (EditText)findViewById(R.id.etUseremail);
        Userpassword = (EditText)findViewById(R.id.etUserpassword);
        Confirmpassword = (EditText) findViewById(R.id.etConfirmpassword);
        Signup = (Button) findViewById(R.id.btnSignUp);
        Login = (TextView) findViewById(R.id.tvLogIn);
        PhoneNumber = (EditText)findViewById(R.id.etPhoneNumber);
        IDNumber = (EditText)findViewById(R.id.etIDNumber);
        Userpic = findViewById(R.id.ivUserPic);
        toolbar = findViewById(R.id.tbmain);
    }
    private Boolean validate(){
        Boolean validation = false;

        namestr = Username.getText().toString();
        emailstr = Useremail.getText().toString();
        passwordstr = Userpassword.getText().toString();
        confirmpasswordstr = Confirmpassword.getText().toString();
        PhoneNumberstr = PhoneNumber.getText().toString();
        IDNumberstr = IDNumber.getText().toString();


        if(namestr.isEmpty()){
            Toast.makeText(this, "Please enter a Username", Toast.LENGTH_SHORT).show();
        }else if(emailstr.isEmpty()){
            Toast.makeText(this, "Please enter an Email Address", Toast.LENGTH_SHORT).show();
        }else if (passwordstr.isEmpty()){
            Toast.makeText(this, "Please enter a Password", Toast.LENGTH_SHORT).show();
        }else if (!passwordstr.equals(confirmpasswordstr)){
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
        }else if(PhoneNumberstr.isEmpty()){
            Toast.makeText(this, "Please enter a Phone Number", Toast.LENGTH_SHORT).show();
        }else if(IDNumberstr.isEmpty()){
            Toast.makeText(this, "Please enter an ID Number", Toast.LENGTH_SHORT).show();
       // }else if(imagePath == null) {
         //   Toast.makeText(this, "Please enter a Profile Picture", Toast.LENGTH_SHORT).show();
        }else{
            validation = true;
        }

        return validation;
    }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        //save data of spam users
                        sendUserData();
                        Toast.makeText(SignUp.this, "User registration successful!" +
                                " Verification email sent!" , Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(SignUp.this, LogIn.class));
                    }else {
                        //when firebase server is down, no internet
                        Toast.makeText(SignUp.this, "Verification email not sent!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserData(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        myRef = databaseReference.child("Users").child(firebaseAuth.getUid());
        //StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile pic"); //like creating a folder for each user, the file being uploaded and the name of the file (User_id/Images/Profile_pic.png)
        //UploadTask uploadTask = imageReference.putFile(imagePath);
        //uploadTask.addOnFailureListener(new OnFailureListener() {
          //  @Override
            //public void onFailure(@NonNull Exception e) {
                //Toast.makeText(SignUp.this, "Image upload failed!",Toast.LENGTH_SHORT).show();
            //}
        //}).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
           // @Override
            //public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //imagePath = taskSnapshot.getDownloadUrl();
              //  Toast.makeText(SignUp.this, "Image upload successful!",Toast.LENGTH_SHORT).show();
            //}
        //});
        UserProfile userProfile = new UserProfile(namestr,emailstr,PhoneNumberstr,IDNumberstr);
        myRef.setValue(userProfile);
    }
    private void initializeToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("InsuranceApp");
    }
}
