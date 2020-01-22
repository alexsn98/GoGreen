package com.example.gogreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gogreen.FirebaseModels.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ValidGreenActivity extends AppCompatActivity {
    ImageView missionImageView;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
    private StorageReference storageReference = storage.getReference();
    private List<String> imageNamesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valid_green);

        getValidGreenImage();

        findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storageReference.child("validGreen/" + imageNamesList.get(0)).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Query query = mFirebaseDatabaseReference.child("GREEN").orderByChild("id");

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};

                                imageNamesList = dataSnapshot.getValue(t);

                                imageNamesList.remove(0);

                                mFirebaseDatabaseReference.child("GREEN").setValue(imageNamesList);

                                getValidGreenImage();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {}
                        });

                        mFirebaseDatabaseReference.child("USERS").child(LoginActivity.getUserLogged().getId()).
                                child("missionsFinished").setValue(LoginActivity.getUserLogged().addMissionsDone());
                    }
                });
            }
        });
    }

    private void getValidGreenImage() {
        missionImageView = findViewById(R.id.missionImage);

        Query query = mFirebaseDatabaseReference.child("GREEN").orderByChild("id");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                };

                imageNamesList = dataSnapshot.getValue(t);

                if (imageNamesList == null) {
                    missionImageView.setImageResource(R.drawable.empty_valid_green);
                    TextView validMissionView = findViewById(R.id.validMissionView);
                    validMissionView.setText("Não há imagens para verificares!" + '\n' + "Volta mais tarde!");
                }

                else {
                    storageReference.child("validGreen/" + imageNamesList.get(0)).getBytes(1024 * 1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            ByteArrayOutputStream blob = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 0, blob);

                            missionImageView.setImageBitmap(bitmap);

                            storageReference.child("validGreen/" + imageNamesList.get(0)).getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                                @Override
                                public void onSuccess(StorageMetadata storageMetadata) {
                                    TextView validMissionView = findViewById(R.id.validMissionView);
                                    validMissionView.setText(storageMetadata.getCustomMetadata("missao"));
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
