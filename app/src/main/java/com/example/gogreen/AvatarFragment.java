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
    View v;

    public AvatarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_avatar, container, false);

        //alterar imagem
        imageView = v.findViewById(R.id.topAvatar);

        imageView.setImageResource(R.drawable.avatar_tree);

        changeAvatar(BitmapFactory.decodeResource(getResources(),R.drawable.avatar_tree));

        return v;
    }

    public void changeAvatar(Bitmap s) {
        imageView = v.findViewById(R.id.topAvatar);

        imageView.setImageBitmap(s);
    }

}
