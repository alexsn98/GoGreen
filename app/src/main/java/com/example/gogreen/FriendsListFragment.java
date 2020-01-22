package com.example.gogreen;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class FriendsListFragment extends Fragment {
    private static final String TAG = "Amigos";
    private PageViewModel pageViewModel;
    private static View v;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
    private List<String> friendsIds;

    public FriendsListFragment() {
        // Required empty public constructor
    }

    public static FriendsListFragment newInstance() {
        return new FriendsListFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        pageViewModel.setIndex(TAG);
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

        friendsIds =  LoginActivity.getUserLogged().getFriends();

        if (friendsIds.get(0).compareTo("0") != 0) {
            refreshFriendList();
        }

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onResume() {
        if (friendsIds.get(0).compareTo("0") != 0) {
            refreshFriendList();
        }

        super.onResume();
    }

    private void refreshFriendList() {
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
