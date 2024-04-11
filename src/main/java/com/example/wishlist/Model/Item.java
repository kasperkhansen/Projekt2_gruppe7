package com.example.wishlist.Model;

public class Item {
    private int id;
    private String Pname;
    private double price;
    private String itemUrl;

    public Item(String Pname, double price, String itemUrl){
        this.Pname = Pname;
        this.price = price;
        this.itemUrl = itemUrl;
    }

    // ------------------- GET and SET

    public int getID() {
        return id;
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
    public String getItemUrl() {
        return itemUrl;
    }
    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

} //item class
