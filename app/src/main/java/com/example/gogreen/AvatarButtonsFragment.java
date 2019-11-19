package com.example.gogreen;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AvatarButtonsFragment extends Fragment {

    private static View v;
    private static int gainedXP = 0;
    private int level;

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

        TextView lvlAtual = v.findViewById(R.id.nivelAtual);
        TextView lvlProx = v.findViewById(R.id.proxNivel);
        TextView xPText = v.findViewById(R.id.xp_number);

        int prox = Integer.valueOf(lvlProx.getText().toString());
        String[] xp = xPText.getText().toString().split("/");


        if(gainedXP + progressBar.getProgress() > progressBar.getMax()){
            int nextLevelXP = progressBar.getProgress() - gainedXP;
            progressBar.setMax(progressBar.getMax() + 1000);
            progressBar.setProgress(0);
            progressBar.incrementProgressBy(nextLevelXP);

            lvlAtual.setText(String.valueOf(prox));
            lvlProx.setText(String.valueOf(prox+1));

            xp[0] = String.valueOf(progressBar.getProgress());
            xp[1] = String.valueOf(progressBar.getMax());

            xPText.setText(xp[0]+"/"+xp[1]);

            level = prox;

        }else{
            progressBar.incrementProgressBy(gainedXP);
            xp[0] = String.valueOf(progressBar.getProgress());
            xPText.setText(xp[0]+"/"+xp[1]);
        }

        gainedXP = 0;
    }

    public int getLevel(){
        return this.level;
    }
}
