package com.example.injury3;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity {

    TextView name;
    TextView email;
    TextView phone;
    TextView address;

    ArrayList<Users> array = new ArrayList<>();

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        final TextView name = findViewById(R.id.name);
        final TextView email = findViewById(R.id.email);
        final TextView phone = findViewById(R.id.phone);
        final TextView address = findViewById(R.id.address);

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
                name.setText("Name: " + array.get(0).getName());
                email.setText("Email: " + array.get(0).getEmail());
                phone.setText("Phone: "+ array.get(0).getPhone());
                address.setText("Address: "+ array.get(0).getAddress());
            }
        });





    }
}
