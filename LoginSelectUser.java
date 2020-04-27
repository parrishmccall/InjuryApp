package com.example.injury3;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LoginSelectUser extends AppCompatActivity {

    private static final String TAG = "LoginSelectUser";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    private EditText email;
    private EditText password;

    private Spinner userTypeSpinner;

    Button login;
    Button cancel;

    private List<String> userType = Arrays.asList("-Select User Type-","Plaintiff", "Attorney");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_select_user);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        userTypeSpinner = findViewById(R.id.userTypeSpinner);
        login = findViewById(R.id.login);
        cancel = findViewById(R.id.cancel);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, userType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in " + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged: no user is currently signed in");
                }
            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1 = String.valueOf(email.getText());
                String password1 = String.valueOf(password.getText());
                if (!isUserSignedIn()) {
                    Log.d(TAG, "There is no current user so sign in.");
                    signIn(email1, password1);
                    if (isUserSignedIn()) {
                        if(userTypeSpinner.getSelectedItem().toString().equals("Plaintiff")){
                            android.content.Intent myIntent = new android.content.Intent(v.getContext(), UserHomeScreen.class);
                            startActivityForResult(myIntent, 0);}
                        else{
                            android.content.Intent myIntent = new android.content.Intent(v.getContext(), LawyerHomescreen.class);
                            startActivityForResult(myIntent, 0);
                        }
                    }
                }

                //CHECKING CURRENT USER LOGIN STATUS TO CHANGE TO HOMEPAGE
                Log.d(TAG, "current user is not null: " + isUserSignedIn());
                if (isUserSignedIn()) {
                    if(userTypeSpinner.getSelectedItem().toString().equals("Plaintiff")){
                    android.content.Intent myIntent = new android.content.Intent(v.getContext(), UserHomeScreen.class);
                    startActivityForResult(myIntent, 0);}
                    else{
                        android.content.Intent myIntent = new android.content.Intent(v.getContext(), LawyerHomescreen.class);
                        startActivityForResult(myIntent, 0);
                    }
                }

            }
        });



    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signing in with:" + email);

        if (!email.equals("") && !password.equals("")) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in successful
                                toastMessage("Successfully signed in!");
                                Log.d(TAG, "signInWithEmail: successful");

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail: failed", task.getException());
                                toastMessage("SignIn unsuccessful! Authentication failed.");
                            }
                        }
                    });
        } else {
            toastMessage("All fields must be filled.");
        }
    }

    private boolean isUserSignedIn() {
        return mAuth.getCurrentUser() != null;
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
