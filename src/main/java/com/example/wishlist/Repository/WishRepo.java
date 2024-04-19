package com.example.wishlist.Repository;

import com.example.wishlist.Model.Item;
import com.example.wishlist.Model.User;
import com.example.wishlist.Model.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
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


    // ------------------- Main functionality methods -------------------

    public void reserveItem(User user, Item item) {
        // update the is_reserved field in the Items table
        String reserveItemSql = "UPDATE Items SET is_reserved = 1 WHERE ID = ?";
        template.update(reserveItemSql, item.getID());

        // insert a row in the ReservedItems table
        String insertReservedItemSql = "INSERT INTO ReservedItems (item_ID, user_ID) VALUES (?, ?)";
        template.update(insertReservedItemSql, item.getID(), user.getID());
    }

    public void unreserveItem(Item item) {
        // update the is_reserved field in the Items table
        String unreserveItemSql = "UPDATE Items SET is_reserved = 0 WHERE ID = ?";
        template.update(unreserveItemSql, item.getID());

        // delete the row from the ReservedItems table
        String deleteReservedItemSql = "DELETE FROM ReservedItems WHERE item_ID = ?";
        template.update(deleteReservedItemSql, item.getID());
    }








    // ------------------- Fetch/Get methods -------------------

    public User getUser(String userName){
        for (User user : fetchAllUsers()){
            if (user.getName().equals(userName)){
                return user;
            }
        }
        return null;
    }

    public List<User> fetchAllUsers(){
        List<User> users = fetchAllUsersEmpty();
        List<User> usersNotNull = new ArrayList<User>();

        for (User u : users){

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

    public List<User> fetchAllUsersEmpty() {
        String sql = "SELECT * FROM Users";
        return template.query(sql, new RowMapper<User>() {
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setID(rs.getInt("ID"));
                user.setName(rs.getString("name"));
                user.setUserPassword(rs.getString("user_password"));
                user.setEmail(rs.getString("email"));
                return user;
            }
        });
    }

    // note: mapRow used for fetching Wishlists and Items when using the foreign keys, since the BeanPropertyRowMapper didn't map Model and Table correctly
    public List<Wishlist> fetchAllWishlistsFrom (User u){
        String sql = "SELECT * FROM Wishlists WHERE user_ID = ?";
        return template.query(sql, new RowMapper<Wishlist>() {
            public Wishlist mapRow(ResultSet rs, int rowNum) throws SQLException {
                Wishlist wishlist = new Wishlist();
                wishlist.setID(rs.getInt("ID"));
                wishlist.setUserID(rs.getInt("user_ID"));
                wishlist.setName(rs.getString("wishlist_name"));

                List<Item> items = fetchAllItemsForWishlist(rs.getInt("ID"));
                wishlist.setItems(items);
                return wishlist;
            }
        }, u.getID());
    }

    public List<Item> fetchAllItemsFrom (Wishlist wl){
        String sql = "SELECT * FROM Items WHERE wishlist_ID = ?";
        return template.query(sql, new RowMapper<Item>() {
            public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
                Item item = new Item();
                item.setID(rs.getInt("ID"));
                item.setWishlistID(rs.getInt("wishlist_ID"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                item.setURL(rs.getString("URL"));
                item.setIsReserved(rs.getBoolean("is_reserved")); // Add this line
                return item;
            }
        }, wl.getID());
    }


    public List<Item> fetchAllItemsForWishlist(int ID){
        String sql = "SELECT * FROM Items WHERE wishlist_ID = ?";
        return template.query(sql, new RowMapper<Item>() {
            public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
                Item item = new Item();
                item.setID(rs.getInt("ID"));
                item.setWishlistID(rs.getInt("wishlist_ID"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                item.setURL(rs.getString("URL"));
                item.setIsReserved(rs.getBoolean("is_reserved"));
                return item;
            }
        }, ID);
    }


    public List<Item> fetchAllItems (){
        String sql = "SELECT * FROM Items";
        return template.query(sql, new RowMapper<Item>() {
            public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
                Item item = new Item();
                item.setID(rs.getInt("ID")); // fetch the new ID field
                item.setWishlistID(rs.getInt("wishlistID"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                item.setURL(rs.getString("URL"));
                return item;
            }
        });
    }

    // ------------------- Add methods -------------------

    public void addFriend(User loggedInUser, User friend) {
        String sql = "INSERT INTO Friends (user_ID, friend_ID) VALUES (?, ?)";
        template.update(sql, loggedInUser.getID(), friend.getID());
    }

    public void addUser(User u){
        String sql = "INSERT INTO Users (name, user_password, email) VALUES (?, ?, ?)";
        template.update(sql, u.getName(), u.getUserPassword(), u.getEmail());
    }

    public void addWishlist(Wishlist wl, User u){
        String sql = "INSERT INTO Wishlists (user_ID, wishlist_name) VALUES (?, ?)";
        template.update(sql, u.getID(), wl.getName());
    }

    public void addItem(Wishlist wl, Item i){
        String sql = "INSERT INTO Items (name, wishlist_ID, price, URL) VALUES (?, ?, ?, ?)";
        template.update(sql, i.getName(), wl.getID(), i.getPrice(), i.getURL());
    }



    // ------------------- Update methods -------------------

    public void updateWishlist(Wishlist wl){
        String sql = "UPDATE wishlists SET wishlist_name = ? WHERE wishlistID = ?";
        template.update(sql, wl.getName(), wl.getID());
    }

    // ------------------- Service methods


    public boolean checkUserExists(User u){
        String sql = "SELECT COUNT(*) FROM Users WHERE name = ?";

        int count = template.queryForObject(sql, Integer.class, u.getName());
        return count > 0;
    }

    public boolean checkWishlistExists(Wishlist wl) {
        String sql = "SELECT COUNT(*) FROM Wishlists WHERE wishlist_name = ?";

        int count = template.queryForObject(sql, Integer.class, wl.getName());
        return count > 0;
    }


    public void cleanupUsers() {
        String sql = "DELETE FROM Users WHERE username IS NULL";
        template.update(sql);
    }

    public void deleteItem(Item item) {
        String sql = "DELETE FROM Items WHERE ID = ?";
        template.update(sql, item.getID());
    }

    public List<User> getFriends(User userDTO) {
        List<User> populatedFriends = new ArrayList<>();
        for (User friend : fetchAllFriends(userDTO)) {
            User user = getUser(friend.getName());
            populatedFriends.add(user);
        }
        return populatedFriends;
    }

    private List<User> fetchAllFriends(User userDTO) {
        String sql = "SELECT * FROM Friends WHERE user_ID = ?";
        return template.query(sql, new RowMapper<User>() {
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setID(rs.getInt("friend_ID"));
                return user;
            }
        }, userDTO.getID());
    }
}
