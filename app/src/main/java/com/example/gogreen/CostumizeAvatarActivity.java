package com.example.gogreen;

import androidx.appcompat.app.AppCompatActivity;

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
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class CostumizeAvatarActivity extends AppCompatActivity  {
    ImageView image;
    Drawable chosen;
    ImageView avatar;
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
        avatar = findViewById(R.id.avatar);
        image =  findViewById(view.getId());

        chosen = image.getDrawable();

        if(image.getColorFilter() == null){
            avatar.setBackground(chosen);
        }


        Intent returnIntent = new Intent();

        Bitmap bitmap = ((BitmapDrawable) chosen).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte [] b = baos.toByteArray();
        returnIntent.putExtra("result", b);
        setResult(Activity.RESULT_OK, returnIntent);
    }

    @Override
    public void onResume(){
        super.onResume();
        if(chosen != null)
        avatar.setBackground(chosen);
    }

//    @Override
//    public void onDestroy(){
//        super.onDestroy();
//        Log.d("pau","Entrou");
//        Intent returnIntent = new Intent();
//        returnIntent.putExtra("result", (Integer) avatar.getTag());
//        setResult(Activity.RESULT_OK, returnIntent);
//    }
}

