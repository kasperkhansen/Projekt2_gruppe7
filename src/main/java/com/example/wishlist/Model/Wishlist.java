package com.example.wishlist.Model;


import java.util.List;

public class Wishlist {
    private int ID;
    private String name;
    private int userID;

    List<Item> items;

    // load-from-table constructor
    public Wishlist(int ID, int userID, String name, List<Item> items){
        this.ID = ID;
        this.userID = userID;
        this.name = name;
        this.items = items;
    }

    public Wishlist () {}

    public Wishlist(String name) {
        this.name = name;
    }




    // ------------------- CRUD Methods for Items

    // ------------------- Create Item
    public void addItem(Item item){
        items.add(item);
    }

    // ------------------- Read Item list
    public List<Item> getAllItems() {
        return items;
    }

    // ------------------- Search for Item by name
    public Item getItemByName(String name){
        for (Item item : items){
            if (item.getName().equals(name)){
                return item;
            }
        }
        return null;
    }

    // ------------------- Update Item
    public void updateItem (Item updatedItem) {
        for (int i = 0 ; i < items.size(); i++) {
            if (items.get(i).getName().equals(updatedItem.getName())){
                items.set(i, updatedItem);
                return;
            }
        }
    }
    // ------------------- Delete Item
    public void deleteItem(String name) {
        items.removeIf(item -> item.getName().equals(name));
    }



    // ------------------- GET and SET

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }
    public void setItems(List<Item> items) {
        this.items = items;
    }

    // toString
    @Override
    public String toString() {
        return "Wishlist{" +
                "ID=" + ID +
                ", ID=" + userID +
                ", wishlist_name='" + name + '\'' +
                ", items=" + items +
                '}';
    }


} //wishlist class

