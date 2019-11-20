package com.example.gogreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MissionsActivity extends AppCompatActivity {
    private static int missionD = 0;
    private static int missionS = 0;
    private static int missionsDone = 0;
    private static ArrayList<Bitmap> missionsBitmaps = new ArrayList<>();
    private static ArrayList<String> missionsText = new ArrayList<>();
    private static String missionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missions);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        final DailyMissionsFragment daily = new DailyMissionsFragment();
        fragmentTransaction.add(R.id.daily_missions_container, daily);

        WeeklyMissionsFragment weekly = new WeeklyMissionsFragment();
        fragmentTransaction.add(R.id.weekly_missions_container,weekly);

        MissionsDoneFragment n_missions = new MissionsDoneFragment();
        fragmentTransaction.add(R.id.missions_done_container,n_missions);

        fragmentTransaction.commit();

        FrameLayout frame = findViewById(R.id.daily_missions_container);
        frame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView dailyMissionTextView = v.findViewById(R.id.dailyMissionText);
                MissionsActivity.setMissionText(dailyMissionTextView.getText().toString());
                dispatchTakePictureIntent(1);
            }
        });

        FrameLayout frame1 = findViewById(R.id.weekly_missions_container);
        frame1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView weeklyMissionTextView = v.findViewById(R.id.weeklyMissionText);
                MissionsActivity.setMissionText(weeklyMissionTextView.getText().toString());
                dispatchTakePictureIntent(2);
            }
        });
    }

    public static int getMissionD() {
        return missionD;
    }

    public static int getMissionS() {
        return missionS;
    }

    public static int getMissionsDone() {
        return missionsDone;
    }

    public static String getMissionText() {
        return missionText;
    }

    public static void setMissionText(String s) {
        missionText = s;
    }

    public static ArrayList<Bitmap> getMissionsImages() {
        return missionsBitmaps;
    }

    public static ArrayList<String> getMissionsTexts() {
        return missionsText;
    }

    private void dispatchTakePictureIntent(int missionType) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, missionType);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            missionsBitmaps.add(imageBitmap);
            Log.d("picha", MissionsActivity.getMissionText());
            missionsText.add(MissionsActivity.getMissionText());

            //counters de missoes
            missionsDone++;

            if (requestCode == 1) missionD++;

            if (requestCode == 2) missionS++;
        }
    }

}
