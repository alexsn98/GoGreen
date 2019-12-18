package com.example.gogreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CostumizeAvatarActivity extends AppCompatActivity  {
    ImageView image;
    Drawable chosenAvatar;
    Drawable chosenBorder;
    ImageView avatarView;
    ColorMatrix matrix;
    static HashMap<Integer, Integer> characters = new HashMap<>();
    private DatabaseReference mFirebaseDatabaseReference;

    static Bitmap bm;
    static Bitmap bmBorder;

    ImageView borderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costumize_avatar);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        //set coins
        TextView coinsView = findViewById(R.id.costumizeCoins);
        coinsView.setText(String.valueOf(LoginActivity.getUserLogged().getCoins()));

        if (characters.size() == 0) {
            characters.put(R.id.character0, R.drawable.avatar_tree);
            characters.put(R.id.character1, R.drawable.bluezao);
            characters.put(R.id.character2, R.drawable.yellowzao);
            characters.put(R.id.character3, R.drawable.greenzao);
            characters.put(R.id.character4, R.drawable.redzao);
            characters.put(R.id.character5, R.drawable.recyclesign1);
        }

        int level = LoginActivity.getUserLogged().getLevel();

        //grey filter
        matrix = new ColorMatrix();
        matrix.setSaturation(0);

        avatarView = findViewById(R.id.avatar);
        borderView = findViewById(R.id.borderView);

        int avatarId = LoginActivity.getUserLogged().getAvatar();
        bm = ((BitmapDrawable) getResources().getDrawable(avatarId)).getBitmap();
        avatarView.setImageBitmap(bm);

        int borderId = LoginActivity.getUserLogged().getMoldura();
        bmBorder = ((BitmapDrawable) getResources().getDrawable(borderId)).getBitmap();
        borderView.setImageBitmap(bmBorder);

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
        /*for(Map.Entry<Integer, Boolean> entry : characters.entrySet()) {
            Integer key = entry.getKey();
            Boolean value = entry.getValue();

            if (!value) {
                ImageView currentView = findViewById(key);
                currentView.setColorFilter(new ColorMatrixColorFilter(matrix));
            }
        }*/

    }

    @Override
    public void onResume(){
        super.onResume();

        avatarView.setImageBitmap(bm);
        borderView.setImageBitmap(bmBorder);

        //if(chosenAvatar != null) avatarView.setBackground(chosenAvatar);
        //if(chosenBorder != null) borderView.setBackground(chosenBorder);

        //set coins
        TextView coinsView = findViewById(R.id.costumizeCoins);
        coinsView.setText(String.valueOf(LoginActivity.getUserLogged().getCoins()));
    }

    public void changeBorder(View view){

        ImageView currentImage = findViewById((R.id.borderView));
        image = findViewById(view.getId());

        chosenBorder = currentImage.getDrawable();

        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();

        if(image.getColorFilter() == null){
            borderView = findViewById(R.id.borderView);
            borderView.setImageBitmap(null);
            borderView.setBackground(image.getDrawable());
            chosenBorder = image.getDrawable();
        }

        Intent returnIntent = new Intent();

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

        chosenAvatar = currentImage.getDrawable();
        int chosenAvatarId = characters.get(view.getId());

        LoginActivity.getUserLogged().setAvatar(chosenAvatarId);
        mFirebaseDatabaseReference.child("USERS").child(LoginActivity.getUserLogged().getId()).child("avatar").setValue(chosenAvatarId);

//        if(characters.get(view.getId())) {
//            avatarView = findViewById(R.id.avatar);
//            avatarView.setImageBitmap(null);
//            avatarView.setBackground(image.getDrawable());
//            chosenAvatar = image.getDrawable();
//        }

//        buyCharacter(view, image.getDrawable());
    }

    public void buyCharacter(final View view, Drawable avatarImage) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_buy, null);

        Drawable popupAvatarImage = avatarImage.getConstantState().newDrawable().mutate();

        final int avatarID = view.getId();
        final Drawable finalAvatarImage = avatarImage;
        //remove saturation
        final ColorMatrix popupMatrix = new ColorMatrix();
        popupMatrix.setSaturation(1);

        // create the popup window
        int width = 900;
        int height = 1100;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        TextView t = popupView.findViewById(R.id.popup_text);
        ImageView avatarImageView = popupView.findViewById(R.id.characterChosen);

        popupAvatarImage.setColorFilter(new ColorMatrixColorFilter(popupMatrix));
        avatarImageView.setBackground(popupAvatarImage);

        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.popupbuy_draw));
        t.setText("C O M P R A R ?");

        popupView.findViewById(R.id.buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginActivity.getUserLogged().getCoins() >= 20) {
                    finalAvatarImage.setColorFilter(new ColorMatrixColorFilter(popupMatrix));

                    //characters.put(avatarID, true);
                    LoginActivity.getUserLogged().setCoins( LoginActivity.getUserLogged().getCoins() - 20);
                    TextView costumizeCoinsView = findViewById(R.id.costumizeCoins);
                    costumizeCoinsView.setText(String.valueOf(LoginActivity.getUserLogged().getCoins()));

                    popupWindow.dismiss();
                }

                else {
                    Toast.makeText(v.getContext(), "Não tens moedas", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //comprar fica disabled se current coins < price

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

