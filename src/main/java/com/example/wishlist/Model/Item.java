package com.example.wishlist.Model;

public class Item {
    private int ID;
    private String name;
    private int wishlistID;
    private double price;
    private String URL;
    private boolean isReserved = false; // default value


    // ------------------- Constructors -------------------
    public Item() {
    }

    // save-to-table
    public Item(String name, double price, String URL){
        this.name = name;
        this.price = price;
        this.URL = URL;
        this.isReserved = false;
    }

    // load-from-table constructor
    public Item(int ID, String name, int wishlistID, double price, String URL, boolean isReserved){
        this.ID = ID;
        this.name = name;
        this.wishlistID = wishlistID;
        this.price = price;
        this.URL = URL;
        this.isReserved = isReserved;
    }



    // ------------------- GET and SET -------------------

    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getWishlistID() {
        System.out.println("DEBUG getID()");
        System.out.println("- "+ wishlistID);
        return wishlistID;
    }
    public void setWishlistID(int wishlistID) {
        this.wishlistID = wishlistID;
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

    public boolean isReserved() {
        return isReserved;
    }
    public void setIsReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }

    @Override
    public String toString() {
        return "Item{" +
                "wishlistID=" + wishlistID +
                ", itemName='" + name + '\'' +
                ", price=" + price +
                ", URL='" + URL + '\'' +
                '}';
    }
}
