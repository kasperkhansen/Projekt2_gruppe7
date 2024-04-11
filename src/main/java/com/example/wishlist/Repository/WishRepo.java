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
        String sql = "INSERT INTO Wishlists (wishlistID, userID, wishlist_name)";
        template.update(sql, wl.getID(), u.getId(), wl.getName());
    }

    public void addItem(Item i){
        String sql = "INSERT INTO Items (Pname, price, URL) VALUES (?, ?, ?)";
        template.update(sql, i.getName(), i.getPrice(), i.getItemUrl());
    }

    public void addUser(User u){
        String sql = "INSERT INTO Users (userID, username, user_password, email)";
        template.update(sql, u.getId(), u.getUsername(), u.getPassWord(), u.getEmail());
    }

    public void updateWishlist(Wishlist wl, int wishlistID){
        String sql = "UPDATE Wishlists SET wishlist_name = ? WHERE wishlistID = ?";
        template.update(sql, wl.getName(), wl.getID());
    }


}
