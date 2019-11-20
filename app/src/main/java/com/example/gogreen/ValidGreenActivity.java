package com.example.gogreen;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ValidGreenActivity extends AppCompatActivity {
    ImageView missionImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valid_green);

        missionImageView = findViewById(R.id.missionImage);
        missionImageView.setImageResource(R.drawable.image_service);

        findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MissionsActivity.getMissionsImages().size() > 0) {
                    Bitmap nextImage = MissionsActivity.getMissionsImages().get(0);
                    missionImageView.setImageBitmap(nextImage);
                    MissionsActivity.getMissionsImages().remove(0);

                    TextView validMissionView = findViewById(R.id.validMissionView);

                    String nextMission = MissionsActivity.getMissionsTexts().get(0);
                    validMissionView.setText(nextMission);
                    MissionsActivity.getMissionsTexts().remove(0);
                }

                else {
                    Toast.makeText(getApplicationContext(), "Sem mais imagens", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
