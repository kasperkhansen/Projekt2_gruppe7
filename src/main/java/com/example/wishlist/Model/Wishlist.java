package com.example.wishlist.Model;


import java.util.List;

public class Wishlist {
    private int w_ID;
    private int u_ID;
    private String w_name;

    List<Item> items;

    public Wishlist(int w_ID, int u_ID, String w_name, List<Item> items){
        this.w_ID = w_ID;
        this.u_ID = u_ID;
        this.w_name = w_name;
        this.items = items;
    }

    // ------------------- CRUD Methods for Items

    // ------------------- Create Item
    public void addItem(Item item){
        items.add(item);
    }

    // ------------------- Read Item list
    public List<Item> getAllItems(){
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
    public void updateItem (Item updateItem) {
        for (int i = 0 ; i < items.size(); i++) {
            if (items.get(i).getName().equals(updateItem.getName())){
                items.set(i, updateItem);
                return;
            }
        }
    }
    // ------------------- Delete Item
    public void deleteItem (String name) {
        for (int i = 0 ; i < items.size(); i++) {
            if (items.get(i).getName().equals(name)){
                items.remove(i);
                return;
            }
        }
    }



    // ------------------- GET and SET
    public int getU_ID() {
        return u_ID;
    }
    public void setU_ID(int u_ID) {
        this.u_ID = u_ID;
    }
    public String getW_name() {
        return w_name;
    }
    public void setW_name(String w_name) {
        this.w_name = w_name;
    }

    public List<Item> getItems() {
        return items;
    }
    public void setItems(List<Item> items) {
        this.items = items;
    }
} //wishlist class

