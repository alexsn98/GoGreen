package com.example.gogreen;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeeklyMissionsFragment extends Fragment {
    private String[] missions = {
            "Apanha 1 garrafa de 1,5L de beatas",
            "Utiliza só uma vez a máquina de lavar roupa",
            "Utiliza uma garrafa reutilizavel durante uma semana",
            "Reutiliza o que tens em casa e cultiva uma planta",
            "Tem o dobro da atenção para não deixares nenhum carregador ligado à ficha nem luzes ligadas",
            "Quando fizeres refeições utiliza só o que tens em casa"

    };

    private View v;

    public WeeklyMissionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_weekly_missions, container, false);

        selectMission();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        selectMission();
    }

    private void selectMission() {
        TextView textView = v.findViewById(R.id.weeklyMissionText);
        int numMission;

        if (LoginActivity.getUserLogged().getMissionW() >= missions.length) {
            if (LoginActivity.getUserLogged().getMissionW() == missions.length)
                numMission = LoginActivity.getUserLogged().getMissionW() % missions.length;


            else
                numMission = (LoginActivity.getUserLogged().getMissionW() % missions.length) - 1;
        }

        else
            numMission = LoginActivity.getUserLogged().getMissionW();

        textView.setText(missions[numMission]);
    }
}
