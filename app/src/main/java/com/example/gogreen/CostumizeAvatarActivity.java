package com.example.gogreen;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CostumizeAvatarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costumize_avatar);

        ImageView imageview = findViewById(R.id.frame1);
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        imageview.setColorFilter(new ColorMatrixColorFilter(matrix));
    }





    public void changeAvatar(View view){
        ImageView avatar = findViewById(R.id.avatar);
        ImageView image =(ImageView) findViewById(view.getId());
        Drawable drawable = image.getDrawable();
        avatar.setImageDrawable(drawable);
        }
}

