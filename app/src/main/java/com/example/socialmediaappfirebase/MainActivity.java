package com.example.socialmediaappfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText edtEmail,edtuse,edtPass;
    private Button btnsignin,btnsignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtEmail=(EditText)findViewById(R.id.editTextTextPersonName);
        edtuse=(EditText)findViewById(R.id.editTextTextPersonName2);
        edtPass=(EditText)findViewById(R.id.editTextTextPersonName3
        );
        btnsignin=(Button)findViewById(R.id.btnsignin);
        btnsignup=(Button)findViewById(R.id.btnsignup);
        mAuth=FirebaseAuth.getInstance();
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });

    }

    @Override
    //run when user logged in and again open app then he directly reach to the socialpage activity
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser!=null){
                transitiontosocialactivity();
        }
    }
    //code for signup
    public void signup(){
        mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(),edtPass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"the authentication is successfully delivered",
                                    Toast.LENGTH_LONG).show();
                            //code for storing name into firebase realtiime database
                            FirebaseDatabase.getInstance().
                                    getReference().child("USERS").
                                    child(Objects.requireNonNull(task.getResult()).getUser().getUid()).
                                    child("username").setValue(edtuse.getText().toString());
                            //if signup complete then we reached to socialpage java
                            transitiontosocialactivity();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"the authentication is failed",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
    //code for sign in activity
    public void signin(){
        mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(),edtPass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"the sign in is successfully delivered",
                                    Toast.LENGTH_LONG).show();
                            transitiontosocialactivity();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"the sign is not done first do sign up",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public void transitiontosocialactivity(){
        Intent i1=new Intent(MainActivity.this,socialpage.class);
        startActivity(i1);
    }
}