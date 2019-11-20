package com.example.gogreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class CostumizeAvatarActivity extends AppCompatActivity  {
    ImageView image;
    Drawable chosenAvatar;
    Drawable chosenBorder;
    ImageView avatar;
    ColorMatrix matrix;
    static HashMap<Integer, Boolean> characters = new HashMap<>();

    static Bitmap bm;
    static Bitmap bmBorder;

    ImageView borderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costumize_avatar);

        //set coins
        TextView coinsView = findViewById(R.id.costumizeCoins);
        coinsView.setText(String.valueOf(AvatarActivity.getCoins()));

        if (characters.size() == 0) {
            characters.put(R.id.character0, true);
            characters.put(R.id.character1, false);
            characters.put(R.id.character2, false);
            characters.put(R.id.character3, false);
            characters.put(R.id.character4, false);
            characters.put(R.id.character5, false);
        }

        int level = AvatarActivity.getLevel();

        //grey filter
        matrix = new ColorMatrix();
        matrix.setSaturation(0);

        avatar = findViewById(R.id.avatar);
        borderView = findViewById(R.id.borderView);

        //change avatar
        if (bm != null) {
            avatar.setImageBitmap(bm);
        }

        else {
            Bitmap defaultAvatar = ((BitmapDrawable) getResources().getDrawable(R.drawable.avatar_tree)).getBitmap();

            avatar.setImageBitmap(defaultAvatar);
        }

        //change border
        if (bmBorder != null) {
            borderView.setImageBitmap(bmBorder);
        }

        else {
            Bitmap defaultBorder = ((BitmapDrawable) getResources().getDrawable(R.drawable.wood_border)).getBitmap();
            borderView.setImageBitmap(defaultBorder);
        }

        //lock das borders
        if(level < 5){
            ImageView silverBorderView = findViewById(R.id.silver);
            silverBorderView.setColorFilter(new ColorMatrixColorFilter(matrix));
        }

        if(level < 10){
            ImageView goldBorderView = findViewById(R.id.gold);
            goldBorderView.setColorFilter(new ColorMatrixColorFilter(matrix));
        }

        //lock characters
        for(Map.Entry<Integer, Boolean> entry : characters.entrySet()) {
            Integer key = entry.getKey();
            Boolean value = entry.getValue();

            if (!value) {
                ImageView currentView = findViewById(key);
                currentView.setColorFilter(new ColorMatrixColorFilter(matrix));
            }
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        if(chosenAvatar != null) avatar.setBackground(chosenAvatar);
        if(chosenBorder != null) borderView.setBackground(chosenBorder);

        //set coins
        TextView coinsView = findViewById(R.id.costumizeCoins);
        coinsView.setText(String.valueOf(AvatarActivity.getCoins()));
    }

    public void changeBorder(View view){
        ImageView currentImage = findViewById((R.id.borderView));
        image = findViewById(view.getId());

        chosenBorder = currentImage.getDrawable();

        if(image.getColorFilter() == null){
            borderView = findViewById(R.id.borderView);
            borderView.setImageBitmap(null);
            borderView.setBackground(chosenBorder);
            chosenBorder = image.getDrawable();
        }

        Intent returnIntent = new Intent();

        Bitmap bitmap = ((BitmapDrawable) chosenBorder).getBitmap();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte [] b = baos.toByteArray();
        returnIntent.putExtra("resultBorder", b);

        bmBorder = bitmap;

        setResult(Activity.RESULT_OK, returnIntent);
    }

    public void changeAvatar(View view){
        ImageView currentImage = findViewById((R.id.avatar));
        image = findViewById(view.getId());

        image = findViewById(view.getId());

        chosenAvatar = currentImage.getDrawable();

        if(image.getColorFilter() == null) {
            avatar = findViewById(R.id.avatar);
            avatar.setImageBitmap(null);
            avatar.setBackground(chosenAvatar);
            chosenAvatar = image.getDrawable();
        }

        else {
            buyCharacter(view);
            //tirar filtro e passar a true no hash map
        }

        Intent returnIntent = new Intent();

        Bitmap bitmap = ((BitmapDrawable) chosenAvatar).getBitmap();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte [] b = baos.toByteArray();
        returnIntent.putExtra("resultAvatar", b);

        bm = bitmap;

        setResult(Activity.RESULT_OK, returnIntent);
    }

    public void buyCharacter(View view) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_fact_card, null);

        // create the popup window
        int width = 900;
        int height = 1100;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        TextView t = popupView.findViewById(R.id.popup_text);

        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.card_brown));
        t.setText("C O M P R A R ?");

        //dar pop up novo com dois butoes e o comprar fica disabled se current coins < price


        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}

