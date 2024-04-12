package com.example.wishlist.Model;

import java.util.List;

public class User {
    public int userID;
    public String username;
    public String user_password;
    private String email;
    private List<Wishlist> wishlists;

    // Constructor used when creating a new user

    public User (String username) {
        this.username = username;
    }

    public User() {
    }

    public User(int userId) {
        this.userID = userId;
    }

    public User(String username, String user_password, String email, List<Wishlist> wishlists) {
        this.username = username;
        this.user_password = user_password;
        this.email = email;
        this.wishlists = wishlists;
    }

    // load-from-table constructor
    public User(int userID, String username, String user_password, String email, List<Wishlist> wishlists){
        this.userID = userID;
        this.username = username;
        this.user_password = user_password;
        this.email = email;
        this.wishlists = wishlists;
    }



    // ------------------- CRUD Methods for Wishlists
    // ------------------- Create Wishlist
    public void addWishlist (Wishlist newList){
        wishlists.add(newList);
    }

    // ------------------- Read List of Wishlists
    public List<Wishlist> getAllWishlists (){
        return wishlists;
    }


    // ------------------- Wishlists for specific UserID
    public void displayWishlistbyUserID (int userID){
        boolean idMatch = false;
        for (Wishlist wishlist : wishlists) {
            if (wishlist.getID() == userID){
                System.out.print(wishlist);
                idMatch = true;
            }
        }
        if (!idMatch) {
            System.out.println("Der blev ikke fundet nogle ønskelister. :-(");
        }
    }
    /*
    public Wishlist getWishlistById(int id) {
        for (Wishlist wishlist : wishlists) {
            if (wishlist.getId() == id) {
                return wishlist;
            }
        }
        return null;
    }
    */

    // ------------------- Get Wishlist By Name(String listName)
    public Wishlist getWishlistByName(String wName) {
        for (Wishlist wishlist : wishlists) {
            if (wishlist.getWishlist_name().equals(wName)) {
                return wishlist;
            }
        }
        return null;
    }

    // ------------------- Update Wishlist
    public void updateWishlist(Wishlist updatedWishlist) {
        int index = wishlists.indexOf(getWishlistByName(updatedWishlist.getWishlist_name()));
        wishlists.remove(index);
        wishlists.set(index, updatedWishlist);
    }


    // ------------------- Delete Wishlist by name
    public void deleteWishlist(String name) {
        for (int i = 0; i < wishlists.size(); i++){
            if (wishlists.get(i).getWishlist_name().equals(name)) {
                wishlists.remove(i);
                return;
            }
        }
        System.out.println("Der ikke fundet nogle ønskelister med dette navn: " + name);
    }

    // ------------------- GET and SET
    public int getId() { return userID; }
    public int getUserID() {
        return userID;
    }
    public String getName() {
        return username;
    }
    public void setUsername(String userName) {
        this.username = userName;
    }
    public String getUser_password() {
        return user_password;
    }
    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public List<Wishlist> getWishlists() { return wishlists; }
    public void setWishlists(List<Wishlist> updatedWishlists) { this.wishlists = updatedWishlists; }

    public void setUserID(int i) {
        this.userID = i;
    }
}
