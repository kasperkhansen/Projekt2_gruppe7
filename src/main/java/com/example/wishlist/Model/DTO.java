package com.example.wishlist.Model;

import com.example.wishlist.Repository.WishRepo;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

public class DTO {
    private User loggedInUser;
    private User userDTO;
    private User userFromDB;
    private Model model;
    private RedirectAttributes redirectAttributes;
    private String wishlistName;
    private String itemName;
    private int itemID;
    private String friendName;
    private String currentPage;
    private Wishlist wishlistDTO;
    private Item itemDTO;

    private List<String> inputs = new ArrayList<>();
    private List<User> friends = new ArrayList<>(); // list of friends for LoggedInUser


    public DTO(User loggedInUser, User userDTO) {
        this.loggedInUser = loggedInUser;
        this.userDTO = userDTO;
    }

    public DTO () {
        this.loggedInUser = new User();
        this.userDTO = new User();
        this.userFromDB = new User();
        this.wishlistDTO = new Wishlist();
        this.itemDTO = new Item();
    }

    // merge userDTO with user from DB


    // CRUD
    public void addInput(String input) {
        this.inputs.add(input);
    }

    // GET and SET methods
    public User getLoggedInUser() {
        return loggedInUser;
    }
    public User getUserDTO() {
        return userDTO;
    }
    public User getUserFromDB() {
        return userFromDB;
    }
    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
    public void setUserDTO(User userDTO) {
        this.userDTO = userDTO;
    }
    public void setUserFromDB(User userFromDB) {
        this.userFromDB = userFromDB;
    }

    // easy SET and GET methods for Wishlist and Items for Data Transfer
    public void setWishlist(Wishlist wishlist) {
        this.userDTO.setWishlist(wishlist);
        addInput(wishlist.getName());
    }

    public Wishlist getWishlist(String wishlistName) {
        return this.userDTO.getWishlist(wishlistName);
    }

    // easy SET and GET methods for Item for Data Transfer
        // wishlistDTO
    public void setWishlistDTO (Wishlist wishlist) {
        this.wishlistDTO = wishlist;
    }
    public Wishlist getWishlistDTO() {
        return this.wishlistDTO;
    }


        // itemDTO is the item that is being transferred => this is for when methods is working with one specific item i.e. reserveItem, deleteItem etc.
    public void setItemDTO(Item item) {
        this.itemDTO = item;
        addInput(item.getName());
    }

        // get Item from wishlistDTO with itemID or itemName
    public Item getItemDTO() {
        return itemDTO;
    }
    public Item getItemDTO(String itemName) {
        for (Item i : this.wishlistDTO.getItems()){
            if (i.getName().equals(itemName)){
                return i;
            }
        }
        return null;
    }


    // get and set Model, RedirectAttributes, wishlistName and itemName
    public Model getModel() {
        return model;
    }
    public RedirectAttributes getRedirectAttributes() {
        return redirectAttributes;
    }
    public String getWishlistName() {
        return wishlistName;
    }
    public String getItemName() {
        return itemName;
    }
    public String getFriendName() {
        return friendName;
    }
    public List<String> getInputs() {
        return inputs;
    }
    public String getCurrentPage() {
        return currentPage;
    }
    public int getItemID() {
        return itemID;
    }
    public List<User> getFriends() {
        return friends;
    }

    public void setModel(Model model) {
        this.model = model;
        addInput("model");
    }
    public void setRedirectAttributes(RedirectAttributes redirectAttributes) {
        this.redirectAttributes = redirectAttributes;
        addInput("redirectAttributes");
    }
    public void setWishlistName(String wishlistName) {
        this.wishlistName = wishlistName;
        addInput("setWishlistName");
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
        addInput("setItemName");
    }
    public void setFriendName(String friendName) {
        this.friendName = friendName;
        addInput("setFriendName");
    }
    public void setInputs(List<String> inputs) {
        this.inputs = inputs;
    }
    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }
    public void setItemID(int itemID) {
        this.itemID = itemID;
        addInput("setItemID");
    }
    public void setFriends(List<User> friends) {
        this.friends = friends;
        addInput("setFriends");
    }


    // toString
    @Override
    public String toString() {
        return "DTO{" +
                "loggedInUser=" + loggedInUser +
                ", userDTO=" + userDTO +
                ", userFromDB=" + userFromDB +
                ", model=" + model +
                ", redirectAttributes=" + redirectAttributes +
                ", wishlistName='" + wishlistName + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemID=" + itemID +
                ", friendName='" + friendName + '\'' +
                ", currentPage='" + currentPage + '\'' +
                ", wishlistDTO=" + wishlistDTO +
                ", itemDTO=" + itemDTO +
                ", inputs=" + inputs +
                ", friends=" + friends +
                '}';
    }
}
