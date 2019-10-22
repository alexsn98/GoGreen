package com.example.gogreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class CostumizeAvatarActivity extends AppCompatActivity  {
    ImageView image;
    Drawable chosen;
    ColorMatrix matrix;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costumize_avatar);

        ImageView imageview = findViewById(R.id.frame1);
        matrix = new ColorMatrix();
        matrix.setSaturation(0);
        imageview.setColorFilter(new ColorMatrixColorFilter(matrix));



    }



    public void changeAvatar(View view){

        ColorMatrix matrix1 = new ColorMatrix();
        matrix1.setSaturation(0);
        ImageView avatar = findViewById(R.id.avatar);
        image =  findViewById(view.getId());

        chosen = image.getDrawable();



        if(image.getColorFilter() == null)
        avatar.setImageDrawable(chosen);

        }
}

