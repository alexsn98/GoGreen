package com.example.gogreen;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gogreen.FirebaseModels.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {
    private static View v;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

    public FriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_friends, container, false);

        recyclerView = v.findViewById(R.id.friendsList);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);

        refreshFriendList();

        // Inflate the layout for this fragment
        return v;
    }

    private void refreshFriendList() {
        final List<String> friendsIds =  LoginActivity.getUserLogged().getFriends();
        final List<User> friends = new ArrayList<>();

        for (final String friendId: friendsIds) {
            Query query = mFirebaseDatabaseReference.child("USERS").child(friendId);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User u = dataSnapshot.getValue(User.class);

                    friends.add(u);

                    if (friends.size() == friendsIds.size()) {
                        AdapterFriends mAdapter = new AdapterFriends(friends);
                        recyclerView.setAdapter(mAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
