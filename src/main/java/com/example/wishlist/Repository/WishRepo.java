package com.example.wishlist.Repository;

import com.example.wishlist.Model.Item;
import com.example.wishlist.Model.User;
import com.example.wishlist.Model.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class WishRepo {


    private final JdbcTemplate template;

    @Autowired
    public WishRepo(JdbcTemplate jdbcTemplate){
        this.template = jdbcTemplate;

        try {
            // This line tries to get a connection to see if it works.
            jdbcTemplate.getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // save, load, delete, update User, wishlist, item


    // ------------------- Fetch methods -------------------


    public List<Item> fetchAllItems (){
        String sql = "SELECT * FROM Items";
        RowMapper<Item> rowMapper = new BeanPropertyRowMapper<>(Item.class);
        return template.query(sql, rowMapper);
    }

        // ------------------- Get methods -------------------

    public User getUser(String username){
        String sql = "SELECT * FROM Users WHERE username = ?";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        return template.queryForObject(sql, rowMapper, username);
    }

    public List<User> fetchAllUsers(){
        List<User> users = fetchAllUsersEmpty();
        List<User> usersNotNull = new ArrayList<User>();

        // go through all users and fill them with their Wishlists and the Items of the wishlists, check for null
        for (User u : users){

            if (u.getName() == null) {
                continue;
            }

            System.out.println("- "+u.getUserID());
            System.out.println("- "+u.getName());
            System.out.println("- "+u.getUser_password());
            System.out.println("- "+u.getEmail());

            List<Wishlist> wishlists = fetchAllWishlistsFrom(u);
            for (Wishlist wl : wishlists){
                List<Item> items = fetchAllItemsFrom(wl);
                wl.setItems(items);
            }
            u.setWishlists(wishlists);
            usersNotNull.add(u);
        }

        return usersNotNull;
    }

    public List<User> fetchAllUsersEmpty(){
        String sql = "SELECT * FROM Users";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        return template.query(sql, rowMapper);
    }

    public List<Wishlist> fetchAllWishlists(){
        String sql = "SELECT * FROM Wishlists";
        RowMapper<Wishlist> rowMapper = new BeanPropertyRowMapper<>(Wishlist.class);
        return template.query(sql, rowMapper);
    }

    public List<Wishlist> fetchAllWishlistsFrom (User u){
        String sql = "SELECT * FROM Wishlists WHERE userID = ?";
        RowMapper<Wishlist> rowMapper = new BeanPropertyRowMapper<>(Wishlist.class);
        return template.query(sql, rowMapper, u.getUserID());
    }

    public List<Item> fetchAllItemsFrom (Wishlist wl){
        String sql = "SELECT * FROM Items WHERE wishlistID = ?";
        RowMapper<Item> rowMapper = new BeanPropertyRowMapper<>(Item.class);
        return template.query(sql, rowMapper, wl.getWishlistID());
    }

    // ------------------- Add methods -------------------

    public void addWishlist(Wishlist wl, User u){
        String sql = "INSERT INTO Wishlists (userID, wishlist_name) VALUES (?, ?)";
        template.update(sql, u.getUserID(), wl.getName());
    }

    public void addItem(Wishlist wl, Item i){
        String sql = "INSERT INTO Items (wishlistID, Pname, price, URL) VALUES (?, ?, ?, ?)";
        template.update(sql, wl.getID(), i.getName(), i.getPrice(), i.getURL());
    }

    public void addUser(User u){
        String sql = "INSERT INTO Users (username, user_password, email) VALUES (?, ?, ?)";
        template.update(sql, u.getName(), u.getUser_password(), u.getEmail());
    }

    // ------------------- Update methods -------------------

    public void updateWishlist(Wishlist wl){
        String sql = "UPDATE Wishlist SET wishlist_name = ? WHERE wishlistID = ?";
        template.update(sql, wl.getName());
    }

    // ------------------- Service methods


    public boolean checkUserExists(User u){
        String sql = "SELECT COUNT(*) FROM Users WHERE username = ?";

        int count = template.queryForObject(sql, Integer.class, u.getName());
        return count > 0;
    }


    public void cleanupUsers() {
        String sql = "DELETE FROM Users WHERE username IS NULL";
        template.update(sql);
    }



}
