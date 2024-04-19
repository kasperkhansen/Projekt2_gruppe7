package com.example.wishlist.Model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class User {
    public int ID;
    public String name;
    public String userPassword;
    private String email;
    private boolean isLoggedIn = false;

    private List<Wishlist> wishlists = new ArrayList<>();


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
    public void setUserPassword(String password) {
        this.userPassword = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public List<Wishlist> getWishlists() { return wishlists; }
    public void setWishlists(List<Wishlist> updatedWishlists) { this.wishlists = updatedWishlists; }

    public Wishlist getWishlist(String name) {
        for (Wishlist wishlist : wishlists) {
            if (wishlist.getName().equals(name)) {
                return wishlist;
            }
        }
        return null;
    }
    public void setWishlist(Wishlist updatedWishlist) {
        for (int i = 0; i < wishlists.size(); i++) {
            if (wishlists.get(i).getName().equals(updatedWishlist.getName())) {
                wishlists.set(i, updatedWishlist);
                return;
            }
        }
    }

    public Item getItem(String itemName) {
        for (Wishlist wl : wishlists){
            for (Item i : wl.getItems()){
                if (i.getName().equals(itemName)){
                    return i;
                }
            }
        }
        return null;
    }
    public Item getItem(int itemID) {
        for (Wishlist wl : wishlists){
            for (Item i : wl.getItems()){
                if (i.getID() == itemID){
                    return i;
                }
            }
        }
        return null;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }


    // ------------------- Password hashing -------------------
    /*
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            String fullHash = bytesToHex(hashedBytes);

            // Let's take only the first 32 characters of the hash
            return fullHash.substring(0, 32);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

     */

    // ------------------- toString -------------------
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
