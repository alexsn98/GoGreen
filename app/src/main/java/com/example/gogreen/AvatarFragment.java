package com.example.gogreen;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AvatarFragment extends Fragment {
    ImageView imageView;
    ImageView borderView;
    View v;
    static Bitmap bmAvatar;
    static Bitmap bmBorder;
    static int level;

    
    public AvatarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_avatar, container, false);

        //alterar imagem
        imageView = v.findViewById(R.id.topAvatar);
        borderView = v.findViewById(R.id.moldura);

        if(bmBorder == null){
            borderView.setImageResource(R.drawable.wood_border);

            changeBorder(BitmapFactory.decodeResource(getResources(),R.drawable.wood_border));
        }else{
            borderView.setImageBitmap(bmBorder);
        }


        if (bmAvatar == null) {
            imageView.setImageResource(R.drawable.avatar_tree);

            changeAvatar(BitmapFactory.decodeResource(getResources(),R.drawable.avatar_tree));
        }
        else {
            imageView.setImageBitmap(bmAvatar);
        }

        //set coins
        TextView coinsView = v.findViewById(R.id.coins);
        coinsView.setText(String.valueOf(AvatarActivity.getCoins()));

        return v;
    }


    public void changeAvatar(Bitmap s) {
        bmAvatar = s;
        imageView = v.findViewById(R.id.topAvatar);

        imageView.setImageBitmap(s);
    }

    public void changeBorder(Bitmap s){
        bmBorder = s;
        borderView = v.findViewById(R.id.moldura);

        borderView.setImageBitmap(s);

    }

    public void setLevel(int level) {
        this.level = level;

        if(level >= 10){
            changeBorder(BitmapFactory.decodeResource(getResources(),R.drawable.gold_border));
        }
        else if(level >= 5){
            changeBorder(BitmapFactory.decodeResource(getResources(),R.drawable.silver_border));
        }
    }
}
