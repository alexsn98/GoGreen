package com.example.gogreen;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class AvatarButtonsFragment extends Fragment {

    private static View v;
    private static int gainedXP = 0;
    public AvatarButtonsFragment() {
        // Required empty public constructor
    }

    public static void increaseXP(int gained_xp) {
        gainedXP = gainedXP + gained_xp;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_avatar_buttons, container, false);

        updateProgressBar();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateProgressBar();
    }

    private void updateProgressBar() {
        ProgressBar progressBar = v.findViewById(R.id.progressBar);



        if(gainedXP > progressBar.getMax()){
            int nextLevelXP = progressBar.getProgress() - gainedXP;
            progressBar.setMax(progressBar.getMax() + 1000);
            progressBar.setProgress(0);
            progressBar.incrementProgressBy(nextLevelXP);
        }else
            progressBar.incrementProgressBy(gainedXP);

        gainedXP = 0;
    }


}
