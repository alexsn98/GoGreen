package com.example.gogreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class AvatarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        Log.d("pau","reset");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        AvatarFragment avatarFragment = new AvatarFragment();
        fragmentTransaction.add(R.id.avatar_frame, avatarFragment);

        AvatarButtonsFragment buttonsFragment = new AvatarButtonsFragment();
        fragmentTransaction.add(R.id.avatar_buttons_frame, buttonsFragment);

        fragmentTransaction.commit();
    }

    public void changeToEnciclopedia(View view) {
        Intent intent = new Intent(this, EnciclopediaActivity.class);
        startActivity(intent);
    }

    public void changeToCostumize(View view) {
        Intent intent = new Intent(this, CostumizeAvatarActivity.class);

        startActivity(intent);
    }

    public void changeToValidGreen(View view) {
        Intent intent = new Intent(this, ValidGreenActivity.class);
        startActivity(intent);
    }
}
