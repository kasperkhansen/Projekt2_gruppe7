package com.example.wishlist.Model;

public class Item {
    private int wishlistID;
    private String Pname;
    private double price;
    private String URL;

    public Item(String Pname, double price, String URL){
        this.Pname = Pname;
        this.price = price;
        this.URL = URL;
    }

    public Item() {

    }

    // load-from-table constructor
    public Item(int wishlistID, String Pname, double price, String URL){
        this.wishlistID = wishlistID;
        this.Pname = Pname;
        this.price = price;
        this.URL = URL;
    }


    // ------------------- GET and SET

        // get and set for thymeleaf calls on the html pages in ID and Name
    public int getWishlistID() {
        return wishlistID;
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
    public String getURL() {
        return URL;
    }
    public void setURL(String URL) {
        this.URL = URL;
    }

} //item class
