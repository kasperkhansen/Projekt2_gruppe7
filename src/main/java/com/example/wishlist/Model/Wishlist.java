package com.example.wishlist.Model;


import java.util.List;

public class Wishlist {
    private int wishlistID;
    private int userID;
    private String wishlist_name;

    List<Item> items;

    // load-from-table constructor
    public Wishlist(int wishlistID, int userID, String wishlist_name, List<Item> items){
        this.wishlistID = wishlistID;
        this.userID = userID;
        this.wishlist_name = wishlist_name;
        this.items = items;
    }

    public Wishlist () {}

    public Wishlist(String wishlist_name) {
        this.wishlist_name = wishlist_name;
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
        return wishlistID;
    }

    public String getName() {
        return wishlist_name;
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

    public String getWishlist_name() {
        return wishlist_name;
    }

    public void setWishlist_name(String wishlist_name) {
        this.wishlist_name = wishlist_name;
    }

    public List<Item> getItems() {
        return items;
    }
    public void setItems(List<Item> items) {
        this.items = items;
    }


} //wishlist class

