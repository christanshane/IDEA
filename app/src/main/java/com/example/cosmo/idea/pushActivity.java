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

public class pushActivity extends AppCompatActivity {
    private EditText emailPush, namePush, pass1Push, pass2Push;
    private Button pushBtn;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        emailPush = (EditText)findViewById(R.id.emailPush);
        namePush = (EditText)findViewById(R.id.namePush);
        pass1Push = (EditText)findViewById(R.id.pass1Push);
        pass2Push = (EditText)findViewById(R.id.pass2Push);
        pushBtn = (Button)findViewById(R.id.pushBtn);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        pushBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailPush.getText().toString();
                String name = namePush.getText().toString();
                String pass1 = pass1Push.getText().toString();
                String pass2 = pass2Push.getText().toString();
                HashMap<String, String> data = new HashMap<>();
                data.put("Name", name);
                data.put("Email", email);
                data.put("Pass", pass1);
                mDatabase.push().setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Pushed", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Pulled", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}
