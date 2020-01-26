package com.example.gogreen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.gogreen.FirebaseModels.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MissionsActivity extends AppCompatActivity {
    private static int missionD = 0;
    private static int missionS = 0;
    private static int missionsDone = 0;
    private static ArrayList<Bitmap> missionsBitmaps = new ArrayList<>();
    private static ArrayList<String> missionsText = new ArrayList<>();
    private static String missionText;
    private static String userID;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
    private String imageName;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missions);
        userID = LoginActivity.getUserLogged().getId();
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

            imageName = "validGreen/" + UUID.randomUUID() + ".png";

            StorageReference mountainsRef = storage.getReference(imageName);
            Bundle extras = data.getExtras();

            Bitmap imageBitmap = (Bitmap) extras.get("data");

            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

            StorageMetadata metadata = new StorageMetadata.Builder()
                    .setCustomMetadata("user", userID)
                    .setCustomMetadata("missao", MissionsActivity.getMissionText()).build();

            byte[] dataByte = baos.toByteArray();

            UploadTask uploadTask = mountainsRef.putBytes(dataByte, metadata);

            uploadTask.addOnCompleteListener(MissionsActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    int duration = Toast.LENGTH_LONG;
                    Toast.makeText(getApplicationContext(), "Concluido!", duration).show();

                    //inserir na lista de nomes de imagens
                    Query query = mFirebaseDatabaseReference.child("GREEN").orderByChild("id");

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};

                            List<String> imageNamesList = dataSnapshot.getValue(t);

                            if (dataSnapshot.getValue() == null) {
                                List<String> newImageNamesList = new ArrayList<>();
                                newImageNamesList.add(imageName.split("/")[1]);
                                mFirebaseDatabaseReference.child("GREEN").setValue(newImageNamesList);
                            }

                            else {
                                imageNamesList.add(imageName.split("/")[1]);

                                mFirebaseDatabaseReference.child("GREEN").setValue(imageNamesList);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            });




            //counters de missoes
            if (requestCode == 1){
                LoginActivity.getUserLogged().addMissionsD();
                mFirebaseDatabaseReference.child("USERS").child(LoginActivity.getUserLogged().getId()).child("missionD").setValue(LoginActivity.getUserLogged().getMissionD());
            }

            if (requestCode == 2){
                LoginActivity.getUserLogged().addMissionsW();
                mFirebaseDatabaseReference.child("USERS").child(LoginActivity.getUserLogged().getId()).child("missionW").setValue(LoginActivity.getUserLogged().getMissionW());


            }
        }
    }

}
