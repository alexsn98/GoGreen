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
public class MissionsDoneFragment extends Fragment {
    View v;

    public MissionsDoneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_missions_done, container, false);

        TextView textView = v.findViewById(R.id.missionsDoneText);
        textView.setText(String.valueOf(LoginActivity.getUserLogged().getMissionsFinished()));
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        TextView textView = v.findViewById(R.id.missionsDoneText);
        textView.setText(String.valueOf(LoginActivity.getUserLogged().getMissionsFinished()));
    }
}
