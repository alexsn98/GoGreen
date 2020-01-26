package com.example.gogreen;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DailyMissionsFragment extends Fragment {
    private String[] missions = {
            "Utiliza transportes públicos",
            "Come uma refeição vegetariana",
            "Planta uma árvore",
            "Recicla uma garrafa de plástico",
            "Utiliza um saco reutilizavel para ir às compras",
            "Faz um esforço pelo o ambiente e toma um banho de água fria",
            "Muda todas as lampadas que tens em casa para LED",
            "Come só refeições vegetarianas durante um dia",
            "Ao longo da semana não compre nem utilize nada com plástico"

    };
    private View v;

    public DailyMissionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_daily_missions, container, false);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        selectMission();
    }

    private void selectMission() {
        TextView textView = v.findViewById(R.id.dailyMissionText);
        int numMission;

        if (LoginActivity.getUserLogged().getMissionD() >= missions.length) {
            if (LoginActivity.getUserLogged().getMissionD() == missions.length)
                numMission = LoginActivity.getUserLogged().getMissionD() % missions.length;


            else
                numMission = (LoginActivity.getUserLogged().getMissionD() % missions.length) - 1;
        }

        else
            numMission = LoginActivity.getUserLogged().getMissionD();

        textView.setText(missions[numMission]);
    }
}
