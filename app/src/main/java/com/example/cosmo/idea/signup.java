package com.example.cosmo.idea;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class signup extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView loginRedirect;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button signupbtn, testBtn;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private EditText emailTxt, nameTxt, pass1Txt, pass2Txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        signupbtn = (Button)findViewById(R.id.signupBtn);
        emailTxt = (EditText)findViewById(R.id.emailTxt);
        nameTxt = (EditText)findViewById(R.id.nameTxt);
        pass1Txt = (EditText)findViewById(R.id.pass1Txt);
        pass2Txt = (EditText)findViewById(R.id.pass2Txt);
        testBtn = (Button)findViewById(R.id.testBtn);
        myRef = FirebaseDatabase.getInstance().getReference();


        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Toast.makeText(getApplicationContext(), "User is Signed in!", Toast.LENGTH_LONG);
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Toast.makeText(getApplicationContext(), "User is Signed out!", Toast.LENGTH_LONG);
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), pushActivity.class));
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailTxt.getText().toString();
                String name = nameTxt.getText().toString();
                String pass1 = pass1Txt.getText().toString();
                String pass2 = pass2Txt.getText().toString();
                if(pass1.equals(pass2)){
                    createAccount(name,email,pass1);
                }
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void createAccount(final String email, final String password, final String name){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if(task.isSuccessful()){
                            Toast.makeText(signup.this,"Registered!", Toast.LENGTH_LONG).show();
                        }

                        if (!task.isSuccessful()) {
                            Toast.makeText(signup.this, "Auth Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signIn(String email, String pass){
        mAuth.signInWithEmailAndPassword(email,pass);
    }

    public void database(String name, String email, String pass){
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("Name", name);
        data.put("Email", email);
        data.put("Pass", pass);
        myRef.push().setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Pushed!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Pulled!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
