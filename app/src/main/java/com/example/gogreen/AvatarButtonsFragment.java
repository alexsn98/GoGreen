package com.example.gogreen;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class AvatarButtonsFragment extends Fragment {

    private static View v;

    private static int gainedXP ;
    private static ProgressBar progressBar;
    private LevelChangeListener listener;
    private DatabaseReference mFirebaseDatabaseReference;

    public interface LevelChangeListener{
        public void onLevelChange(int level);
    }

    public AvatarButtonsFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_avatar_buttons, container, false);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        gainedXP = LoginActivity.getUserLogged().getXp();

        //progressBar = AvatarActivity.getProgressBar();

        progressBar = v.findViewById(R.id.progressBar);
        updateProgressBar();

        return v;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateProgressBar() {
        TextView lvlAtualView = v.findViewById(R.id.nivelAtual);
        TextView lvlProxView = v.findViewById(R.id.proxNivel);
        TextView xPTextView = v.findViewById(R.id.xp_number);

        int currentLevel = LoginActivity.getUserLogged().getLevel();
        int currentXP = LoginActivity.getUserLogged().getXp();

        progressBar.setMax(LoginActivity.getUserLogged().getLevel() * 1000);

        progressBar.setProgress(0);
        progressBar.incrementProgressBy(currentXP);

        if (currentXP >= progressBar.getMax()) {
            currentLevel++;

            currentXP = Math.abs(progressBar.getMax() - currentXP);

            progressBar.setMax(progressBar.getMax() + 1000);

            progressBar.setProgress(0);
            progressBar.incrementProgressBy(currentXP);

            LoginActivity.getUserLogged().setXp(currentXP);
            LoginActivity.getUserLogged().setLevel(currentLevel);

            mFirebaseDatabaseReference.child("USERS").child(LoginActivity.getUserLogged().getId()).child("xp").setValue(currentXP);
            mFirebaseDatabaseReference.child("USERS").child(LoginActivity.getUserLogged().getId()).child("level").setValue(currentLevel);
        }

        lvlAtualView.setText(String.valueOf(currentLevel));
        lvlProxView.setText(String.valueOf(currentLevel + 1));

        xPTextView.setText(currentXP + "/" + currentLevel * 1000);
    }


    public void setLevelChangeListener(LevelChangeListener listener) {
        this.listener = listener;
    }
}
