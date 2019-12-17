package com.example.gogreen.FirebaseModels;

import java.util.ArrayList;

public class User {

    private String id;
    private String name;
    private int level;
    private int xp;
    private String avatar;
    private String moldura;
    private int missionsFinished;
    private ArrayList<User> friends;
    private int coins;
    private ArrayList<String> cards;

    public User(String id , String name){
        this.id = id;
        this.level = 0;
        this.xp = 0;
        this.name = name;
        this.avatar = "avatar_tree";
        this.moldura = "wood_border";
        friends = new ArrayList<>();
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMoldura() {
        return moldura;
    }

    public void setMoldura(String moldura) {
        this.moldura = moldura;
    }

    public int getMissionsFinished() {
        return missionsFinished;
    }

    public void setMissionsFinished(int missionsFinished) {
        this.missionsFinished = missionsFinished;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public ArrayList<String> getCards() {
        return cards;
    }

    public void setCards(ArrayList<String> cards) {
        this.cards = cards;
    }

    public void addFriend(User u){
        friends.add(u);
    }

    public void addCard(String card){
        this.cards.add(card);
    }
}
