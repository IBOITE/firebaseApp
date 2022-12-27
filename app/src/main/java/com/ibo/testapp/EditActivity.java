package com.ibo.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class EditActivity extends AppCompatActivity {
    private EditText name, imgurl,oldemail, newemail , password,oldpass,newpass;
    private Button update1,update2,update3;
    private FirebaseUser user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        name=findViewById(R.id.name);
        oldemail=findViewById(R.id.old_email);
        newemail=findViewById(R.id.new_email);
        password=findViewById(R.id.password);
        imgurl=findViewById(R.id.imgUrl);
        update1=findViewById(R.id.update1);
        update2=findViewById(R.id.update2);
        oldpass=findViewById(R.id.old_pass);
        newpass=findViewById(R.id.new_pass);
        update3=findViewById(R.id.update_pass);
        user= FirebaseAuth.getInstance().getCurrentUser();
        initial();
    }

    private void initial() {
        getProfile();
        update1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserProfileChangeRequest profileUpdates=new UserProfileChangeRequest.Builder()
                        .setDisplayName(name.getText().toString())
                        .setPhotoUri(Uri.parse(imgurl.getText().toString()))
                        .build();
                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                System.out.println(task);
                                if(task.isSuccessful()){
                                    Toast.makeText(EditActivity.this,"Update Successfully",Toast.LENGTH_LONG).show();
                                    finish();
                                }else {
                                    Toast.makeText(EditActivity.this,"Error...",Toast.LENGTH_LONG).show();

                                }
                            }
                        });
            }
        });
        update2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthCredential credential= EmailAuthProvider.getCredential(oldemail.getText().toString(),password.getText().toString());
                //Prompt the user to re-proviede their sign-in credentials
                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            user.updateEmail(newemail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(EditActivity.this,"email updated successfully",Toast.LENGTH_LONG).show();
                                        finish();
                                    }else {
                                        Toast.makeText(EditActivity.this,"Error email",Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        }else {
                            Toast.makeText(EditActivity.this,"Error email",Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });
        update3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthCredential credential=EmailAuthProvider.getCredential(oldemail.getText().toString(),oldpass.getText().toString());
                //Prompt the user to re-proviede their sign-in credentials
                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            user.updatePassword(newpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(EditActivity.this,"sucssuful",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(EditActivity.this,"erorr",Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });
    }

    private void getProfile() {
        if (user!=null){
            name.setText(user.getDisplayName());
            oldemail.setText(user.getEmail());
            if (user.getPhotoUrl()!=null){
                imgurl.setText(user.getPhotoUrl().toString());
            }
        }
    }
}