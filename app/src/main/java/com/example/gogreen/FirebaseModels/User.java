package com.example.gogreen.FirebaseModels;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.example.gogreen.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {

    private String id;
    private String name;
    private int level;
    private int xp;
    private int avatar;
    private int moldura;
    private int missionsFinished;
    private List<Integer> friends;
    private List<Integer> avatars;
    private int coins;
    private List<String> cards;

    public User(String id , String name){
        this.id = id;
        this.level = 1;
        this.xp = 0;
        this.name = name;
        this.avatar = R.drawable.avatar_tree;
        this.moldura = R.drawable.wood_border;
        this.avatars = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.coins = 0;
        this.cards = new ArrayList<>();
    }


    public User(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public int getMoldura() {
        return moldura;
    }

    public List<Integer> getAvatars() {
        return avatars;
    }

    public void addAvatar(int avatar) {
        this.avatars.add(avatar);
    }

    public void setBorder(int chosenBorderId) {

    }
}
