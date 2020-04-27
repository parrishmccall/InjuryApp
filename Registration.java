package com.example.injury3;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Registration extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    private EditText name;
    private EditText email;
    private EditText password1;
    private EditText password2;
    private EditText phone;
    private String authPassword;
    private EditText address;
    private static final String TAG = "EmailPasswordActivity";

    Button register;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

//        cancel.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View view){
//                android.content.Intent myIntent = new android.content.Intent(view.getContext(),
//                        LandingPage.class);
//                startActivityForResult(myIntent, 0);
//            }
//        });



        name = (EditText) findViewById(R.id.practiceName);
        email = (EditText) findViewById(R.id.email);
        password1 = (EditText) findViewById(R.id.password1);
        password2 = (EditText) findViewById(R.id.password2);
        phone = (EditText) findViewById(R.id.phone);
        address = (EditText) findViewById(R.id.address);

        register = findViewById(R.id.register);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            }
        };

        register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String name1 = String.valueOf(name.getText());
                String email1 = String.valueOf(email.getText());
                String password11 = String.valueOf(password1.getText());
                String password22 = String.valueOf(password2.getText());
                String phone1 = String.valueOf(phone.getText());
                String address1 = String.valueOf(address.getText());


                setAuthPassword(password11, password22);
                if(name1.isEmpty() || email1.isEmpty() || password11.isEmpty() || password22.isEmpty()
                        || phone1.isEmpty()){
                    toastMessage("All fields not filled in");
                }
                else if(authPassword != null) {
                    createAccount(email1, password22);
                    addToDatabase(name1, email1, address1, phone1);
                }
            }
        });



    }

    private void setAuthPassword(String pass1, String pass2){
        if(!pass1.equals(pass2)){
            toastMessage("Passwords do not match");
        }
        if(pass1.length() < 6 || pass2.length()<5){
            toastMessage("Password must be at least 6 characters long");
        }
        if(pass1.equals(pass2) && pass2.length()>5){
            authPassword = pass2;
        }
    }

    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void createAccount(String authEmail, String authPassword) {
        System.out.print("Trying to register");
        mAuth.createUserWithEmailAndPassword(authEmail, authPassword).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            toastMessage("Account Created");
                        }
                        else{
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            toastMessage("Account Creation Failed");
                        }

                    }
                }
        );
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void addToDatabase(String name, String email, String address, String phone){
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);
        user.put("address", address);
        user.put("phone", phone);

        db.collection("users").document(email).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
            }
        });
    }


}


    /**@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    } **/



   /**

    mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
@Override
public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
        // Sign in success, update UI with the signed-in user's information
        Log.d(TAG, "createUserWithEmail:success");
        FirebaseUser user = mAuth.getCurrentUser();
        updateUI(user);
        } else {
        // If sign in fails, display a message to the user.
        Log.w(TAG, "createUserWithEmail:failure", task.getException());
        Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
        Toast.LENGTH_SHORT).show();
        updateUI(null);
        }

        // ...
        }
        }); **/


   /** Sign in existing users
   mAuth.signInWithEmailAndPassword(email, password)
           .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
@Override
public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
        // Sign in success, update UI with the signed-in user's information
        Log.d(TAG, "signInWithEmail:success");
        FirebaseUser user = mAuth.getCurrentUser();
        updateUI(user);
        } else {
        // If sign in fails, display a message to the user.
        Log.w(TAG, "signInWithEmail:failure", task.getException());
        Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
        Toast.LENGTH_SHORT).show();
        updateUI(null);
        }

        // ...
        }
        });
    **/

   /** Access User Information
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    if (user != null) {
    // Name, email address, and profile photo Url
    String name = user.getDisplayName();
    String email = user.getEmail();
    Uri photoUrl = user.getPhotoUrl();

    // Check if user's email is verified
    boolean emailVerified = user.isEmailVerified();

    // The user's ID, unique to the Firebase project. Do NOT use this value to
    // authenticate with your backend server, if you have one. Use
    // FirebaseUser.getIdToken() instead.
    String uid = user.getUid();
    } **/