package com.example.gogreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
        setContentView(R.layout.activity_avatar);

        if(xp[1] == 0) xp[1] = 1000;



        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        avatarFragment = new AvatarFragment();
        fragmentTransaction.add(R.id.avatar_frame, avatarFragment);

        AvatarButtonsFragment buttonsFragment = new AvatarButtonsFragment();
        fragmentTransaction.add(R.id.avatar_buttons_frame, buttonsFragment);

        buttonsFragment.setLevelChangeListener(new LevelChangeListener() {
            @Override
            public void onLevelChange(int level) {
                avatarFragment.setLevel(level);
            }
        });

        FriendsFragment friendsFragment = new FriendsFragment();
        fragmentTransaction.add(R.id.avatar_all_frame, friendsFragment);

        fragmentTransaction.commit();

        username = LoginActivity.getUsername();
        //alterar nome
        TextView t = findViewById(R.id.usernameView);

        t.setText(username);

        screenAvatar = findViewById(R.id.avatarScreen);
        Intent i = getIntent();

        if(i.getStringExtra("string_qr") != null) {
            String s = (i.getStringExtra("string_qr"));
            int duration = Toast.LENGTH_LONG;
            Toast.makeText(getApplicationContext(), s, duration).show();
            i.removeExtra("string_qr");
        }

        screenAvatar.setOnTouchListener(new OnSwipeTouchListener(AvatarActivity.this){
            @Override
            public void onSwipeRight() {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

                findViewById(R.id.avatar_all_frame).setVisibility(View.GONE);

                findViewById(R.id.avatar_frame).setVisibility(View.VISIBLE);

                findViewById(R.id.avatar_buttons_frame).setVisibility(View.VISIBLE);

                findViewById(R.id.underlineView).setVisibility(View.VISIBLE);

                findViewById(R.id.underlineView2).setVisibility(View.GONE);

                fragmentTransaction.commit();
            }

            @Override
            public void onSwipeLeft() {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

                findViewById(R.id.avatar_frame).setVisibility(View.GONE);

                findViewById(R.id.avatar_buttons_frame).setVisibility(View.GONE);

                findViewById(R.id.avatar_all_frame).setVisibility(View.VISIBLE);

                findViewById(R.id.underlineView).setVisibility(View.GONE);

                findViewById(R.id.underlineView2).setVisibility(View.VISIBLE);

                fragmentTransaction.commit();
            }
        });
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

    public class OnSwipeTouchListener implements OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener (Context ctx){
            gestureDetector = new GestureDetector(ctx, new GestureListener());
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                            result = true;
                        }
                    }
                    else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                        result = true;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeRight() {
        }

        public void onSwipeLeft() {
        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {
        }
    }
}
