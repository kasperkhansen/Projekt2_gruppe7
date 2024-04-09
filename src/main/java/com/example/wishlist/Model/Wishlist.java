package com.example.wishlist.Model;


public class Wishlist {
    private int w_ID;
    private int u_ID;
    private String w_name;
    public Wishlist(int w_ID, int u_ID, String w_name){
        this.w_name = w_name;

    }
    public String getW_name() {
        return w_name;
    }

    public void setW_name(String w_name) {
        this.w_name = w_name;
    }
} //wishlist class

