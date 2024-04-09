package com.example.wishlist.Model;

import java.util.List;

public class User {
    public int id;
    public String name;
    public String p_word;
    private String email;
    private List<Wishlist> wishlists;


    public User(int id, String u_name, String p_word, String email, List<Wishlist> wishlists){
        this.id = id;
        this.name = u_name;
        this.p_word = p_word;
        this.email = email;
        this.wishlists = wishlists;
    }

    // getters og setter. ID er tilegnet fra database, så ingen setter
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String u_name) {
        this.name = u_name;
    }
    public String getP_word() {
        return p_word;
    }
    public void setP_word(String p_word) {
        this.p_word = p_word;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public List<Wishlist> getWishlists() { return wishlists; }
    public void setWishlists(List<Wishlist> updatedWishlists) { this.wishlists = updatedWishlists; }
}
