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

public class MissionsActivity extends AppCompatActivity {
    private static int missionD = 0;
    private static int missionS = 0;
    private static int missionsDone = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missions);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        DailyMissionsFragment daily = new DailyMissionsFragment();
        fragmentTransaction.add(R.id.daily_missions_container, daily);

        WeeklyMissionsFragment weekly = new WeeklyMissionsFragment();
        fragmentTransaction.add(R.id.weekly_missions_container,weekly);

        MissionsDoneFragment n_missions = new MissionsDoneFragment();
        fragmentTransaction.add(R.id.missions_done_container,n_missions);

        fragmentTransaction.commit();

        FrameLayout frame = findViewById(R.id.daily_missions_container);
        frame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dispatchTakePictureIntent(1);
            }
        });

        FrameLayout frame1 = findViewById(R.id.weekly_missions_container);
        frame1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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

    private void dispatchTakePictureIntent(int missionType) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, missionType);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            missionD++;
            missionsDone++;

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //imageView.setImageBitmap(imageBitmap);
        }

        if (requestCode == 2 && resultCode == RESULT_OK) {
            missionS++;
            missionsDone++;

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //imageView.setImageBitmap(imageBitmap);
        }
    }

}
