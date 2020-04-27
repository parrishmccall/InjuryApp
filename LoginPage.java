package com.example.injury3;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LoginPage extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //FirebaseFirestore db = FirebaseFirestore.getInstance();

    private EditText username;
    private EditText password;

    private String userType = null;
    private boolean loop = true;

    Button login;
    Button back;

    private ArrayList<Users> array = new ArrayList<>();
    private ArrayList<Users> array2 = new ArrayList<>();
    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        login = findViewById(R.id.login);
        back = findViewById(R.id.back);

        username = findViewById(R.id.email);
        password = findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in " + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    //User is signed out
                    Log.d(TAG, "onAuthStateChanged: no user is currently signed in");
                    //toastMessage("Successfully signed out.");
                }
            }
        };

//        login.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View view){
//                String email = String.valueOf(username.getText());
//                String password1 = String.valueOf(password.getText());
//
//                if (!isUserSignedIn()) {
//                    Log.d(TAG, "There is no current user so sign in.");
//                    //signIn(email, password1);
//                    signIn(email, password1);
//                    android.content.Intent myIntent = new android.content.Intent(view.getContext(), UserHomeScreen.class);
//                    startActivityForResult(myIntent, 0);
//
//                }
//                Log.d(TAG, "current user is not null: " + isUserSignedIn());
//                if (isUserSignedIn()) {
//                    android.content.Intent myIntent = new android.content.Intent(view.getContext(), UserHomeScreen.class);
//                    startActivityForResult(myIntent, 0);
//                }
//            }
//        }

        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String email = String.valueOf(username.getText());
                String password1 = String.valueOf(password.getText());
                    signIn(email, password1);
                    if (isUserSignedIn()) {
                        isUser(email);
                    }
                        if (array.size() > 0 || array2.size() > 0) {
                            if (array.size() > 0 && array.get(0).getEmail().equals(email)) {
                                android.content.Intent myIntent = new android.content.Intent(view.getContext(), LawyerHomescreen.class);
                                startActivityForResult(myIntent, 0);

                            }
                            if (array2.size() > 0 && array2.get(0).getEmail().equals(email)) {
                                android.content.Intent myIntent = new android.content.Intent(view.getContext(), UserHomeScreen.class);
                                startActivityForResult(myIntent, 0);

                            }
                        }
            }
        }
        );



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
//    private void signIn(String email, String password){
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                        }
//                    }
//                });
//    }





    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private boolean isUserSignedIn() {
        return mAuth.getCurrentUser() != null;
    }

//    private boolean isUser(String email) {
//        DocumentReference docRef = db.collection("users").document(email);
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        userType1 = true;
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                        toastMessage("user exists");
//
//                    } else {
//                        Log.d(TAG, "No such document");
//                        userType1 = false;
//                        toastMessage("lawyer exists");
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });
//        return userType1;
//    }

    private void isUser(String email){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference email2 = db.collection("lawyers");
        CollectionReference email3 = db.collection("users");
        Query usersQuery = email2.whereEqualTo("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        Query usersQuery2 = email3.whereEqualTo("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        usersQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot doc: task.getResult()){
                        Users user4 = doc.toObject(Users.class);
                        array.add(user4);
                        System.out.print("added to array");
                    }
                }
                else{
                    System.out.println("Task unsuccessful");
                }
            }
        });
        usersQuery2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot doc: task.getResult()){
                        Users user4 = doc.toObject(Users.class);
                        array2.add(user4);
                        System.out.print("added to array");
                    }
                }
                else{
                    System.out.println("Task unsuccessful");
                }
            }
        });

    }
}
