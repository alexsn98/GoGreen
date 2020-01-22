package com.example.gogreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.gogreen.AvatarButtonsFragment.LevelChangeListener;
import com.google.android.material.tabs.TabLayout;


public class AvatarActivity extends AppCompatActivity {
    LinearLayout screenAvatar;
    AvatarFragment avatarFragment;
    static String username;

    private static int gainedXP = 0;
    private static int level = 1;
    private static int[] xp = {0,0};
    private static int coins = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avatar_view_pager);

        //if(xp[1] == 0) xp[1] = 1000;

        //FragmentManager fragmentManager = getSupportFragmentManager();
        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

//        avatarFragment = new AvatarFragment();
//        fragmentTransaction.add(R.id.avatar_frame, avatarFragment);
//
//        AvatarButtonsFragment buttonsFragment = new AvatarButtonsFragment();
//        fragmentTransaction.add(R.id.avatar_buttons_frame, buttonsFragment);
//
//        buttonsFragment.setLevelChangeListener(new LevelChangeListener() {
//            @Override
//            public void onLevelChange(int level) {
//                avatarFragment.setLevel(level);
//            }
//        });
//
//        FriendsFragment friendsFragment = new FriendsFragment();
//        fragmentTransaction.add(R.id.avatar_all_frame, friendsFragment);
//
//        fragmentTransaction.commit();

        TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(tabsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


        username = LoginActivity.getUsername();

        Intent i = getIntent();

        if(i.getStringExtra("string_qr") != null) {
            String s = (i.getStringExtra("string_qr"));
            int duration = Toast.LENGTH_LONG;
            Toast.makeText(getApplicationContext(), s, duration).show();
            i.removeExtra("string_qr");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

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

    public void changeToSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void addFriends(View view) {
        Intent intent = new Intent(this, AddFriendsActivity.class);
        startActivity(intent);
    }
}
