package com.example.wishlist.Model;

import java.util.List;

public class User {
    public int id;
    public String userName;
    public String passWord;
    private String email;

    List<Wishlist> wishlists;

    public User(int id, String userName, String passWord, String email){
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
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
            if (wishlist.getWishlistName().equals(wName)) {
                return wishlist;
            }
        }
        return null;
    }

    // ------------------- Update Wishlist
    public void updateWishlist(Wishlist updatedWishlist) {
        int index = wishlists.indexOf(getWishlistByName(updatedWishlist.getWishlistName()));
        wishlists.remove(index);
        wishlists.set(index, updatedWishlist);
    }


    // ------------------- Delete Wishlist by name
    public void deleteWishlist(String name) {
        for (int i = 0; i < wishlists.size(); i++){
            if (wishlists.get(i).getWishlistName().equals(name)) {
                wishlists.remove(i);
                return;
            }
        }
        System.out.println("Der ikke fundet nogle ønskelister med dette navn: " + name);
    }

    // ------------------- GET and SET
    public int getId() {
        return id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
