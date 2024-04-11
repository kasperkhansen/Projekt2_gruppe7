package com.example.wishlist.Repository;

import com.example.wishlist.Model.Item;
import com.example.wishlist.Model.User;
import com.example.wishlist.Model.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class WishRepo {


    private final JdbcTemplate template;

    @Autowired
    public WishRepo(JdbcTemplate jdbcTemplate){
        this.template = jdbcTemplate;
    }
    // save, load, delete, update User, wishlist, item

    public List<Item> fetchAll (){
        String sql = "SELECT * FROM Items";
        RowMapper<Item> rowMapper = new BeanPropertyRowMapper<>(Item.class);
        return template.query(sql, rowMapper);
    }

    public void addWishlist(Wishlist wl, User u){
        String sql = "INSERT INTO Wishlist (userID, wishlist_name) VALUES (?, ?)";
        template.update(sql, u.getId(), wl.getName());
    }

    public void addItem(Wishlist wl, Item i){
        String sql = "INSERT INTO Items (wishlistID, Pname, price, URL) VALUES (?, ?, ?, ?)";
        template.update(sql, wl.getID(), i.getName(), i.getPrice(), i.getItemUrl());
    }

    public void addUser(User u){
        String sql = "INSERT INTO Users (username, user_password, email) VALUES (?, ?, ?)";
        template.update(sql, u.getName(), u.getPassWord(), u.getEmail());
    }

    public void updateWishlist(Wishlist wl){
        String sql = "UPDATE Wishlist SET wishlist_name = ? WHERE wishlistID = ?";
        template.update(sql, wl.getName());
    }


}
