package com.example.wishlist.Test;

import com.example.wishlist.Model.Item;
import com.example.wishlist.Repository.WishRepo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class WishRepoTest {

    @Autowired
    private WishRepo wishRepo;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testFetchAll() {
        Item item1 = new Item("item1", 100.0, "url1");
        Item item2 = new Item("item2", 200.0, "url2");

        List<Item> expectedItems = Arrays.asList(item1, item2);

        // mock jdbcTemplate.query to return our test items when called in wishRepo.fetchAll()
        String sql = "SELECT * FROM Items";
        when(jdbcTemplate.query(sql, any(RowMapper.class))).thenReturn(expectedItems);

        List<Item> actualItems = wishRepo.fetchAll();

        assertEquals(expectedItems, actualItems);
    }

    // mock additional tests for addWishlist(), addItem(), addUser(), updateWishlist()
    // you could follow the same pattern
}