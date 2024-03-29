package com.example.gogreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gogreen.FirebaseModels.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1;
    private static boolean doLogin = true;
    static GoogleSignInClient mGoogleSignInClient;
    static GoogleSignInAccount gs;
    private static String username;

    private DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
    private static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide(); //esconder app bar


        //login auth google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("36264021532-jl8jllfkqvk95ve1bit1md9ae8rrft7p.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        if(doLogin)
            setUpAccount();

        else {
            ProgressBar p = findViewById(R.id.loading);
            p.setVisibility(View.INVISIBLE);
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    signIn(v);
                }
            });
        }
    }


    public void setUpAccount(){
        gs = GoogleSignIn.getLastSignedInAccount(this);

        if (gs != null) {
            ProgressBar p = findViewById(R.id.loading);
            p.setVisibility(View.VISIBLE);
            p.setIndeterminate(true);

            setUsername(gs.getDisplayName());
            findUser(gs, new userCallBack() {
                @Override
                public void getCallback(User u) {
                    user = u;
                }
            });


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 1000);
        }

        else {
            ProgressBar p = findViewById(R.id.loading);
            p.setVisibility(View.INVISIBLE);
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    signIn(v);
                }
            });
        }
    }
    public static String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.split(" ")[0];
    }

    private void signIn(View v) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public static void logout() {
        if(gs!= null){
            mGoogleSignInClient.signOut();
            doLogin = false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                // Google Sign-In was successful, authenticate with Firebase
                final GoogleSignInAccount account = result.getSignInAccount();
                setUsername(account.getDisplayName().split(" ")[0]);

                findUser(account, new userCallBack() {
                    @Override
                    public void getCallback(User u) {
                        user = u;
                    }
                });

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();

            } else {
                // Google Sign-In failed
                Log.e("TAG", "Google Sign-In failed.");
            }
        }
    }
    private void findUser(final GoogleSignInAccount account, final userCallBack userCallBack){
        Query query = mFirebaseDatabaseReference.child("USERS").orderByChild("id").equalTo(account.getId());

        final boolean[] b = {false};
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        User u = ds.getValue(User.class);
                        if(u.getId().compareTo(account.getId()) == 0){

                            GenericTypeIndicator<List<Integer>> t = new GenericTypeIndicator<List<Integer>>() {};
                            List<Integer> avatars =   ds.child("AVATARS").getValue(t);

                            GenericTypeIndicator<List<Integer>> t1 = new GenericTypeIndicator<List<Integer>>() {};
                            List<Integer> cards =  ds.child("CARDS").getValue(t1);

                            GenericTypeIndicator<List<Integer>> t2 = new GenericTypeIndicator<List<Integer>>() {};
                            List<Integer> cardsToGive =  ds.child("CARDSTOGIVE").getValue(t2);

                            GenericTypeIndicator<List<String>> t3 = new GenericTypeIndicator<List<String>>() {};
                            List<String> friends =  ds.child("FRIENDS").getValue(t3);



                            u.setAvatars(avatars);
                            u.setCards(cards);
                            u.setCardsToGive(cardsToGive);
                            u.setFriends(friends);

                            user = u;
                            b[0] = true;
                            userCallBack.getCallback(user);
                        }
                }

                if(!b[0]) {
                    User u = new User(account.getId(), getUsername());
                    mFirebaseDatabaseReference.child("USERS").child(account.getId()).setValue(u);

                    u.addAvatar(R.id.character0);
                    mFirebaseDatabaseReference.child("USERS").child(account.getId()).child("AVATARS").setValue(u.getAvatars());

                    u.addCard(0);
                    mFirebaseDatabaseReference.child("USERS").child(account.getId()).child("CARDS").setValue(u.getCards());

                    u.addCardToGive(0);
                    mFirebaseDatabaseReference.child("USERS").child(account.getId()).child("CARDSTOGIVE").setValue(u.getCardsToGive());

                    u.addFriend("0");
                    mFirebaseDatabaseReference.child("USERS").child(account.getId()).child("FRIENDS").setValue(u.getFriends());

                    user = u;
                    userCallBack.getCallback(user);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public static User getUserLogged(){
        return user;
    }

    private interface userCallBack{
        void getCallback(User u);
    }
}
