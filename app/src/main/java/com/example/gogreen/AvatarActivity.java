package com.example.gogreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Line;


public class AvatarActivity extends AppCompatActivity {
    LinearLayout screenAvatar;
    AvatarFragment avatarFragment;
    static String s_popup;
    static boolean popup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        avatarFragment = new AvatarFragment();
        fragmentTransaction.add(R.id.avatar_frame, avatarFragment);

        AvatarButtonsFragment buttonsFragment = new AvatarButtonsFragment();
        fragmentTransaction.add(R.id.avatar_buttons_frame, buttonsFragment);

        FriendsFragment friendsFragment = new FriendsFragment();
        fragmentTransaction.add(R.id.avatar_all_frame, friendsFragment);

        fragmentTransaction.commit();



        //alterar nome
        TextView t = findViewById(R.id.usernameView);
        t.setText(LoginActivity.getUsername());

        screenAvatar = findViewById(R.id.avatarScreen);

        if(popup){
            popUpOfXp();
            popup = false;
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

    public void changeToEnciclopedia(View view) {
        Intent intent = new Intent(this, EnciclopediaActivity.class);
        startActivity(intent);
    }

    public void changeToCostumize(View view) {
        Intent intent = new Intent(this, CostumizeAvatarActivity.class);

        startActivityForResult(intent, 1);
    }

    public void changeToValidGreen(View view) {
        Intent intent = new Intent(this, ValidGreenActivity.class);
        startActivity(intent);
    }

    public void changeToSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);

        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras();
                byte[] result = extras.getByteArray("result");
                Bitmap bm = BitmapFactory.decodeByteArray(result,0,result.length);
                avatarFragment.changeAvatar(bm);
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
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

    public void popUpOfXp(){
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_fact_card, null);

        // create the popup window
        int width = 900;
        int height = 1100;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        TextView t = popupView.findViewById(R.id.popup_text);
        t.setText(s_popup);
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(screenAvatar.getContext(), R.drawable.card_brown));
    }

    public static void setPopup(String s, boolean b){
        s_popup = s;
        popup = b;
    }
}
