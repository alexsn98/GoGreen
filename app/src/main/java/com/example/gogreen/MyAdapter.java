package com.example.gogreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gogreen.FirebaseModels.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<User> mDataset;
    private DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
    private Context activityContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView nameView;
        public TextView levelView;
        public TextView missionsDone;
        public ImageView avatarView;
        public Button add;

        public MyViewHolder(View v) {
            super(v);
            nameView = v.findViewById(R.id.name);
            levelView = v.findViewById(R.id.level);
            avatarView = v.findViewById(R.id.avatar);
            missionsDone = v.findViewById(R.id.missionsDone);
            add = v.findViewById(R.id.add);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<User> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        activityContext = parent.getContext();
        View v = LayoutInflater.from(activityContext)
                .inflate(R.layout.friend_card, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final User u = mDataset.get(position);
        holder.nameView.setText(u.getName());
        holder.levelView.setText("Nivel " + u.getLevel());
        holder.missionsDone.setText(u.getMissionsFinished()+"");

        holder.avatarView.setImageResource(u.getAvatar());

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(LoginActivity.getUserLogged().getFriends().get(0).compareTo("0") == 0){
                    List<String> list =  LoginActivity.getUserLogged().getFriends();
                    list.set(0,u.getId());

                    LoginActivity.getUserLogged().setFriends(list);
                }else
                    LoginActivity.getUserLogged().addFriend(u.getId());

                if(u.getFriends().get(0).compareTo("0") == 0){
                    List<String> list =  u.getFriends();
                    list.set(0,LoginActivity.getUserLogged().getId());
                    u.setFriends(list);
                }else
                    u.addFriend(LoginActivity.getUserLogged().getId());

                mFirebaseDatabaseReference.child("USERS").child(LoginActivity.getUserLogged().getId()).child("FRIENDS").setValue(LoginActivity.getUserLogged().getFriends());
                mFirebaseDatabaseReference.child("USERS").child(u.getId()).child("FRIENDS").setValue(u.getFriends());

                ((Activity)activityContext).finish();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
