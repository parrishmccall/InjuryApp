package com.example.injury3;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserHomeScreen extends AppCompatActivity {

    ArrayList<Users> array = new ArrayList<>();

    private FirebaseAuth mAuth;

    Button newCase;
    Button existing;
    Button logOut;
    Button profile;
    TextView welcomeText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_screen);


        final TextView welcomeText = (TextView) findViewById(R.id.welcomeText);

        mAuth = FirebaseAuth.getInstance();
        String user = mAuth.getCurrentUser().getEmail();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference collection = db.collection("users");
            Query query = collection.whereEqualTo("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot doc: task.getResult()){
                            Users user = doc.toObject(Users.class);
                            array.add(user);
                        }
                    }
                    welcomeText.setText("Welcome " + array.get(0).getName());
                }
            });






        newCase = findViewById(R.id.newCase);
        existing = findViewById(R.id.existing);
        logOut = findViewById(R.id.logOut);
        profile = findViewById(R.id.profile);

        newCase.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                android.content.Intent myIntent = new android.content.Intent(view.getContext(), UserNewCase.class);
                startActivityForResult(myIntent, 0);
            }
        });

        profile.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                android.content.Intent myIntent = new android.content.Intent(view.getContext(), UserProfile.class);
                startActivityForResult(myIntent, 0);
            }
        });


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                FirebaseAuth.getInstance().signOut();
                android.content.Intent myIntent = new android.content.Intent(view.getContext(), LoginPage.class);
                startActivityForResult(myIntent, 0);
            }
        });

        existing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.content.Intent myIntent = new android.content.Intent(view.getContext(), UserExistingCases.class);
                startActivityForResult(myIntent, 0);
            }
        });

    }

//    private void getUserData(String email){
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        CollectionReference collection = db.collection("users");
//        Query query = collection.whereEqualTo("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
//        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    for(QueryDocumentSnapshot doc: task.getResult()){
//                        Users user = doc.toObject(Users.class);
//                        array.add(user);
//                    }
//                }
//            }
//        });
//    }
}


