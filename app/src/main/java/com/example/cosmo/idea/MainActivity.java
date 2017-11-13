package com.example.cosmo.idea;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private EditText nodeName, nodeEmail;
    private Button mFirebaseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nodeName = (EditText)findViewById(R.id.nodeNameTxt);
        nodeEmail = (EditText)findViewById(R.id.nodeEmailTxt);
        mFirebaseBtn = (Button)findViewById(R.id.firebaseBtn);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mFirebaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nodeName.getText().toString();
                String email = nodeEmail.getText().toString();

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("Name", name);
                data.put("Email", email);

                mDatabase.push().setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Data is stored!", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
