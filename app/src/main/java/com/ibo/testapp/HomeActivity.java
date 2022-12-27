package com.ibo.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    private TextView name,email, uid,isverified,imgurl;
    private Button btnLogout,edit,verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        name =findViewById(R.id.name);
        email =findViewById(R.id.email);
        uid=findViewById(R.id.uid);
        isverified=findViewById(R.id.isVerified);
        imgurl=findViewById(R.id.imgUrl);
        edit=findViewById(R.id.edit);
        btnLogout=findViewById(R.id.logout);
        verify=findViewById(R.id.verify);
        initial();
    }

    private void initial() {
        getProfile();
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent();
                intent.setClass(HomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(HomeActivity.this,EditActivity.class);
                startActivity(intent);
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth=FirebaseAuth.getInstance();
                FirebaseUser user=auth.getCurrentUser();
                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(HomeActivity.this,"send",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(HomeActivity.this,"error",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    private void getProfile() {
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            name.setText(user.getDisplayName());
            email.setText(user.getEmail());
            if(user.getPhotoUrl()!=null){
                imgurl.setText(user.getPhotoUrl().toString());
            }
            boolean emailVerified=user.isEmailVerified();
            if (emailVerified){
                isverified.setText("verified");
            }else{
                isverified.setText("not verified");
            }
            uid.setText(user.getUid());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProfile();
    }
}