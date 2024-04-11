package com.example.wishlist.Repository;

import com.example.wishlist.Model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WishRepoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void addUser() {

        // 1. Given: Prepare test data - a user
        User user = new User();

        user.setUsername("username");
        user.setPassWord("password");
        user.setEmail("email@test.com");

        // 2. When: Call add user method
        WishRepo wishRepo = new WishRepo(jdbcTemplate);
        wishRepo.addUser(user);

        // 3. Then: Check if the user is in the database
        String sql = "SELECT * FROM Users WHERE userID = ?";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        User retrievedUser = jdbcTemplate.queryForObject(sql, rowMapper, user.getId());

        assertNotNull(retrievedUser);
        assertEquals(user.getUsername(), retrievedUser.getUsername());
        assertEquals(user.getPassWord(), retrievedUser.getPassWord());
        assertEquals(user.getEmail(), retrievedUser.getEmail());
    }
}