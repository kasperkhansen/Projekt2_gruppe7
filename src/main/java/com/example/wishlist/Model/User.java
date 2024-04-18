package com.example.wishlist.Model;

import java.util.List;

public class User {
    public int ID;
    public String name;
    public String userPassword;
    private String email;
    private boolean isLoggedIn = false; // added this to keep track of whether the user is logged in or not

    private List<Wishlist> wishlists;


    // Constructor used when creating a new user

    public User() {
    }

    public User (String name) {
        this.name = name;
    }

    public User(int userId) {
        this.ID = userId;
    }

    // save-to-table constructor
    public User(String name, String userPassword, String email, List<Wishlist> wishlists) {
        this.name = name;
        this.userPassword = userPassword;
        this.email = email;
        this.wishlists = wishlists;
    }

    // load-from-table constructor (doesn't have isLoggedIn, since this shouldn't be saved to DB)
    public User(int ID, String name, String userPassword, String email, List<Wishlist> wishlists){
        this.ID = ID;
        this.name = name;
        this.userPassword = userPassword;
        this.email = email;
        this.wishlists = wishlists;
        this.isLoggedIn = false;
    }

    // full constructor of User (with isLoggedIn)
    public User(int ID, String name, String userPassword, String email, List<Wishlist> wishlists, boolean isLoggedIn){
        this.ID = ID;
        this.name = name;
        this.userPassword = userPassword;
        this.email = email;
        this.wishlists = wishlists;
        this.isLoggedIn = isLoggedIn;
    }


    // -----------

    public List<Wishlist> getAllWishlists (){
        return wishlists;
    }

    // ------------------- CRUD Methods for Wishlists
    // ------------------- Create Wishlist


    // ------------------- Read List of Wishlists


    // ------------------- CRUD methods for Wishlists of User -------------------
    public void addWishlist (Wishlist newList){
        wishlists.add(newList);
    }

    public void displayWishlists() {
        for (Wishlist wishlist : wishlists) {
            System.out.println(wishlist);
        }
    }

    public void updateWishlist(Wishlist updatedWishlist) {
        // wishlist name is unique
        // -> find the index of the old wishlist to update using name

        int index = wishlists.indexOf(updatedWishlist.getName());
        wishlists.remove(index);
        wishlists.set(index, updatedWishlist);
    }

    public void deleteWishlist(String name) {
        for (int i = 0; i < wishlists.size(); i++){
            if (wishlists.get(i).getName().equals(name)) {
                wishlists.remove(i);
                return;
            }
        }
        System.out.println("Der ikke fundet nogle ønskelister med dette navn: " + name);
    }
    public void deleteWishlist(Wishlist wl) {
        wishlists.remove(wl);
    }

    // ------------------- GET and SET -------------------
    public int getID() {
        return ID;
    }
    public void setID(int id) {
        this.ID = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String userName) {
        this.name = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public List<Wishlist> getWishlists() { return wishlists; }
    public void setWishlists(List<Wishlist> updatedWishlists) { this.wishlists = updatedWishlists; }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }



    // tostring
    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", user password='" + userPassword + '\'' +
                ", email='" + email + '\'' +
                ", wishlists=" + wishlists +
                '}';
    }
}
