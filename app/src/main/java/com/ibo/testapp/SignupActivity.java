 package com.ibo.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

 public class SignupActivity extends AppCompatActivity {

     private EditText email, password;
     private TextView login;
     private Button signup;
     private ProgressBar progressBar;

     private FirebaseAuth mAuth;

     @SuppressLint("MissingInflatedId")
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


         mAuth = FirebaseAuth.getInstance();


         email=findViewById(R.id.email);
         password=findViewById(R.id.password);
         signup=findViewById(R.id.signup);
         login=findViewById(R.id.login);
         progressBar=findViewById(R.id.progress);

         initial();
    }

     private void initial() {
         login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent= new Intent();
                 intent.setClass(SignupActivity.this,MainActivity.class);
                 startActivity(intent);
                 finish();

             }
         });

         signup.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 signupWithEmail(email.getText().toString(),password.getText().toString());
             }
         });
     }

     private void signupWithEmail(String em, String pass){
         if(em.length()>4 &&pass.length()>6){
             //create account with firebase
             progressBar.setVisibility(View.VISIBLE);


             mAuth.createUserWithEmailAndPassword(em, pass)
                     .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             progressBar.setVisibility(View.GONE);

                             if (task.isSuccessful()) {
                                 // Sign in success, update UI with the signed-in user's information

                                 FirebaseUser user = mAuth.getCurrentUser();

                             } else {
                                 // If sign in fails, display a message to the user.
                                 Toast.makeText(SignupActivity.this, "Authentication failed.",
                                         Toast.LENGTH_SHORT).show();
                             }
                         }
                     });
         }else{
             if(em.length()<4){
                 email.setError("Email not valid");
             }
             if(em.length()<4){
                 password.setError("password short");
             }
         }
     }
 }