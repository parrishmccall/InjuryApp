package com.example.injury3;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.io.ObjectStreamException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserNewCase extends AppCompatActivity {


    private List<String> caseTypes = Arrays.asList("Motor vehicle accidents",
            "Medical malpractice", "Product liability", "Premises liability",
            "Nursing home negligence", "Construction accidents", "Serious injuries",
            "Wrongful death");

    private Spinner spinner;
    private EditText date;
    private EditText description;

    Button submit;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_new_case);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userEmail = user.getEmail();



        spinner = (Spinner) findViewById(R.id.spinner);
        date = findViewById(R.id.date);
        description = findViewById(R.id.description);
        submit = findViewById(R.id.submit);



        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, caseTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                spinner = (Spinner) findViewById(R.id.spinner);
                date = findViewById(R.id.date);
                description = findViewById(R.id.description);
                submit = findViewById(R.id.submit);
                String spinnerText = spinner.getSelectedItem().toString();
                String date1 = date.getText().toString();
                String description1 = description.getText().toString();
                addToDatabase(spinnerText, date1, description1, userEmail);
                toastMessage("Case Submitted");
                android.content.Intent myIntent = new android.content.Intent(view.getContext(), UserHomeScreen.class);
                startActivityForResult(myIntent, 0);

            }
        });

    }
    private void addToDatabase(String spinner, String date, String description, String userEmail){
        Map<String, Object> newData = new HashMap<>();
        newData.put("accident_type", spinner);
        newData.put("date", date);
        newData.put("description", description);

        //db.collection("users").document(userEmail).set(newData, SetOptions.merge());
        DocumentReference ref = db.collection("users").document(userEmail).collection("cases")
                .document();
        ref.set(newData);
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}