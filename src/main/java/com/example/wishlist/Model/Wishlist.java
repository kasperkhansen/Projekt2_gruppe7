package com.example.wishlist.Model;


import java.util.List;

public class Wishlist {
    private int wishlistID;
    private final int id;
    private int userID;
    private String wishlistName;

    List<Item> items;

    public Wishlist(int wishlistID, int userID, String wishlistName, List<Item> items){
        this.wishlistID = wishlistID;
        this.userID = userID;
        this.wishlistName = wishlistName;
        this.items = items;
        this.id = wishlistID;
    }

    // ------------------- CRUD Methods for Items

    // ------------------- Create Item
    public void addItem(Item item){
        items.add(item);
    }

    // ------------------- Read Item list
    public List<Item> getAllItems() {
        return items;
    }

    // ------------------- Search for Item by name
    public Item getItemByName(String name){
        for (Item item : items){
            if (item.getName().equals(name)){
                return item;
            }
        }
        return null;
    }

    // ------------------- Update Item
    public void updateItem (Item updatedItem) {
        for (int i = 0 ; i < items.size(); i++) {
            if (items.get(i).getName().equals(updatedItem.getName())){
                items.set(i, updatedItem);
                return;
            }
        }
    }
    // ------------------- Delete Item
    public void deleteItem(String name) {
        items.removeIf(item -> item.getName().equals(name));
    }



    // ------------------- GET and SET
    public int getID() {
        return id;
    }

    public String getName() {
        return wishlistName;
    }

    public int getWishlistID() {
        return wishlistID;
    }

    public void setWishlistID(int wishlistID) {
        this.wishlistID = wishlistID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getWishlistName() {
        return wishlistName;
    }

    public void setWishlistName(String wishlistName) {
        this.wishlistName = wishlistName;
    }

    public List<Item> getItems() {
        return items;
    }
    public void setItems(List<Item> items) {
        this.items = items;
    }


} //wishlist class

