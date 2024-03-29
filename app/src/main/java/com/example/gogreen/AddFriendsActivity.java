package com.example.gogreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.gogreen.FirebaseModels.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddFriendsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        recyclerView = findViewById(R.id.searchList);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void searchFriends(View view) {
        final List<User> searchResult =  new ArrayList<>();
        TextView searchName = findViewById(R.id.searchField);

        Query query = mFirebaseDatabaseReference.child("USERS").orderByChild("name")
                .startAt(searchName.getText().toString())
                .endAt(searchName.getText().toString()+"\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    User u = ds.getValue(User.class);
                    if(u.getId().compareTo(LoginActivity.getUserLogged().getId()) != 0 ) {
                        GenericTypeIndicator<List<String>> t3 = new GenericTypeIndicator<List<String>>() {};
                        List<String> friends = ds.child("FRIENDS").getValue(t3);

                        if (!friends.contains(LoginActivity.getUserLogged().getId())) {
                            u.setFriends(friends);
                            searchResult.add(u);
                        }
                    }
                }

                MyAdapter mAdapter = new MyAdapter(searchResult);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
