package com.example.wishlist.Model;

public class User {
    public int id;
    public String u_name;
    public String p_word;
    private String email;


    public User(int id, String u_name, String p_word, String email){
        this.id = id;
        this.u_name = u_name;
        this.p_word = p_word;
        this.email = email;
    }

    // getters og setter. ID er tilegnet fra database, så ingen setter
    public int getId() {
        return id;
    }
    public String getU_name() {
        return u_name;
    }
    public void setU_name(String u_name) {
        this.u_name = u_name;
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
}
