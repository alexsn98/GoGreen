package com.example.gogreen;

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

public class AdapterFriends extends RecyclerView.Adapter<AdapterFriends.MyViewHolder> {
    private List<User> mDataset;
    private DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView nameView;
        public TextView levelView;
        public TextView missionsDone;
        public ImageView avatarView;

        public MyViewHolder(View v) {
            super(v);
            nameView = v.findViewById(R.id.name);
            levelView = v.findViewById(R.id.level);
            avatarView = v.findViewById(R.id.avatar);
            missionsDone = v.findViewById(R.id.missionsDone);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterFriends(List<User> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdapterFriends.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_card1, parent, false);

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
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
