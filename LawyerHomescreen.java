package com.example.injury3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class LawyerHomescreen extends AppCompatActivity {

    private FirebaseAuth mAuth;

    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_homescreen);

        mAuth = FirebaseAuth.getInstance();

        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                FirebaseAuth.getInstance().signOut();
                android.content.Intent myIntent = new android.content.Intent(view.getContext(), LoginPage.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }
}
