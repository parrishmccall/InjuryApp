package com.example.injury3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegTypeSelection extends AppCompatActivity {


    Button lawyer;
    Button user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_type_selection);

        lawyer = findViewById(R.id.lawyer);
        user = findViewById(R.id.user);

        lawyer.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                android.content.Intent myIntent = new android.content.Intent(view.getContext(),
                        LawyerRegistration.class);
                startActivityForResult(myIntent, 0);
            }
        });

        user.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                android.content.Intent myIntent = new android.content.Intent(view.getContext(),
                        Registration.class);
                startActivityForResult(myIntent, 0);
            }
        });

    }
}
