package com.example.gogreen;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
            "Utiliza um saco reutilizavel para ir às compras"
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

        TextView textView = v.findViewById(R.id.dailyMissionText);
        textView.setText(missions[MissionsActivity.getMissionD()]);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        TextView textView = v.findViewById(R.id.dailyMissionText);
        textView.setText(missions[MissionsActivity.getMissionD()]);
    }
}
