package com.example.wishlist.Model;

public class Items {
    private int w_ID;
    private String Pname;
    private double price;
    private String i_URL;

    public Items(int w_ID, String Pname, double price, String i_URL){
        this.Pname = Pname;
        this.price = price;
    }

    public int getW_ID(){
        return w_ID;
    }
    public String getName() {
        return Pname;
    }
    public void setName(String name) {
        this.Pname = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getI_URL() {
        return i_URL;
    }
    public void setI_URL(String i_URL) {
        this.i_URL = i_URL;
    }

} //item class
