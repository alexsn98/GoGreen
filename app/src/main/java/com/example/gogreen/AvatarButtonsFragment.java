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

    private static int gainedXP ;
    private static int level;
    private static ProgressBar progressBar;
    private static int[] xp ;
    private LevelChangeListener listener;

    public interface LevelChangeListener{
        public void onLevelChange(int level);
    }

    public AvatarButtonsFragment() {
        // Required empty public constructor
    }

    public static void increaseXP(int received_xp) {
        AvatarActivity.setGainedXP(AvatarActivity.getGainedXP() + received_xp);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_avatar_buttons, container, false);

        gainedXP = LoginActivity.getUserLogged().getXp();

        //progressBar = AvatarActivity.getProgressBar();

        progressBar = v.findViewById(R.id.progressBar);


        xp = AvatarActivity.getXp();

        progressBar.setProgress(LoginActivity.getUserLogged().getXp());
        progressBar.setMax(LoginActivity.getUserLogged().getLevel() * 1000);
        updateProgressBar();

        return v;
    }


    private void updateProgressBar() {
        TextView lvlAtualView = v.findViewById(R.id.nivelAtual);
        TextView lvlProxView = v.findViewById(R.id.proxNivel);
        TextView xPTextView = v.findViewById(R.id.xp_number);


        if(gainedXP + xp[0] >= progressBar.getMax()){
            Log.d("pau1", String.valueOf(gainedXP+progressBar.getProgress()));
            int nextLevelXP = Math.abs(progressBar.getMax() - (xp[0] + gainedXP));

            progressBar.setMax(progressBar.getMax() + 1000);
            progressBar.setProgress(0);
            progressBar.incrementProgressBy(nextLevelXP);

            lvlAtualView.setText(String.valueOf(++level));
            lvlProxView.setText(String.valueOf(level+1));

            xp[0] = nextLevelXP;
            xp[1] = progressBar.getMax();

            xPTextView.setText(xp[0]+"/"+xp[1]);


            if (listener != null) {
                listener.onLevelChange(level);
            }
        }

        else{
            Log.d("pixa", String.valueOf(gainedXP));
            progressBar.setProgress(xp[0] + gainedXP);

            xp[0] = progressBar.getProgress();
            xp[1] = progressBar.getMax();
            lvlAtualView.setText(String.valueOf(level));
            lvlProxView.setText(String.valueOf(level+1));
            xPTextView.setText(xp[0]+"/"+xp[1]);

        }
        AvatarActivity.setGainedXP(0);
        AvatarActivity.setXp(xp);
        AvatarActivity.setLevel(level);

    }



    public void setLevelChangeListener(LevelChangeListener listener) {
        this.listener = listener;
    }
}
