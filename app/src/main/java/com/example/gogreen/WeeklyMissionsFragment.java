package com.example.gogreen;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeeklyMissionsFragment extends Fragment {
    private String[] missions = {
            "Apanha 1 garrafa de 1,5L de beatas",
            "Não desperdices comida",
            "Utiliza só uma vez a máquina de lavar roupa",
            "Utiliza uma garrafa reutilizavel durante uma semana",
            "Coloca lâmpadas economicas na casa toda"
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

        TextView textView = v.findViewById(R.id.weeklyMissionText);
        textView.setText(missions[MissionsActivity.getMissionS()]);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        TextView textView = v.findViewById(R.id.weeklyMissionText);
        textView.setText(missions[MissionsActivity.getMissionS()]);
    }
}
