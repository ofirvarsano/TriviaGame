package com.example.a8lab307.myapplication;

import com.hit.communication.Result;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    private String user;
    private String pass;
    private int diff;
    private int score;
    public static ArrayList<Result> results;

    public Player(String user, String pass, int diff, int score) {
        this.user = user;
        this.pass = pass;
        this.diff = diff;
        this.score = score;
    }


    Player(Player p)
    {
        this.user = p.getUser();
        this.pass = p.getPass();
        this.diff = p.getDiff();
        this.score = p.getScore();
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
