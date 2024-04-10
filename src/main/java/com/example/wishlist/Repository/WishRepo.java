package com.example.wishlist.Repository;

import com.example.wishlist.Model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishRepo {
    @Autowired
    JdbcTemplate template;

    public List<Item> fetchAll (){
        String sql = "SELECT * FROM Items";
        RowMapper<Item> rowMapper = new BeanPropertyRowMapper<>(Item.class);
        return template.query(sql, rowMapper);
    }

    public void addItem(Item i){
        String sql = "INSERT INTO Items (Pname, price, i_URL) VALUES (?, ?, ?)";
        template.update(sql, i.getName(), i.getPrice(), i.getItemUrl());
    }




}
