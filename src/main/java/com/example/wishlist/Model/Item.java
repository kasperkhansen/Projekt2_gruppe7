package com.example.wishlist.Model;

public class Item {
    private int wishlistID;
    private String itemName;
    private double price;
    private String URL;

    public Item(String itemName, double price, String URL){
        this.itemName = itemName;
        this.price = price;
        this.URL = URL;
    }

    public Item() {

    }

    // load-from-table constructor
    public Item(int wishlistID, String itemName, double price, String URL){
        this.wishlistID = wishlistID;
        this.itemName = itemName;
        this.price = price;
        this.URL = URL;
    }


    // ------------------- GET and SET

        // get and set for thymeleaf calls on the html pages in ID and Name
    public int getWishlistID() {
        System.out.println("DEBUG getWishlistID()");
        System.out.println("- "+ wishlistID);
        return wishlistID;
    }
    public void setWishlistID(int wishlistID) {
        this.wishlistID = wishlistID;
    }
    public String getItemName() {
        System.out.println("DEBUG getItemName()");
        System.out.println("- "+ itemName);
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
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

    @Override
    public String toString() {
        return "Item{" +
                "wishlistID=" + wishlistID +
                ", itemName='" + itemName + '\'' +
                ", price=" + price +
                ", URL='" + URL + '\'' +
                '}';
    }
} //item class
