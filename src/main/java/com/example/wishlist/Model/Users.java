package com.example.wishlist.Model;

public class Users {
    private int id;
    private String u_name;
    private String p_word;
    private String email;

    public Users (int id, String u_name, String p_word, String email){
        this.id = id;
        this.u_name = u_name;
        this.p_word = p_word;
        this.email = email;
    }

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
