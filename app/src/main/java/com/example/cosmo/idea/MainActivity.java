package com.example.cosmo.idea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private EditText nodeName, nodeValue;
    private Button mFirebaseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nodeName = (EditText)findViewById(R.id.nodeNameTxt);
        nodeValue = (EditText)findViewById(R.id.nodeValueTxt);
        mFirebaseBtn = (Button)findViewById(R.id.firebaseBtn);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mFirebaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nodeName.getText().toString();
                String value = nodeValue.getText().toString();
                mDatabase.child(name).setValue(value);
            }
        });
    }
}
