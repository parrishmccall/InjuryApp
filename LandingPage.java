package com.example.injury3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LandingPage extends AppCompatActivity {

    Button login;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                android.content.Intent myIntent = new android.content.Intent(view.getContext(), RegTypeSelection.class);
                startActivityForResult(myIntent, 0);
            }
        });

        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                android.content.Intent myIntent = new android.content.Intent(view.getContext(), LoginPage.class);
                startActivityForResult(myIntent, 0);
            }
        });



    }
}
