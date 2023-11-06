package com.crypto.SpringSecurity.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {


    @Test
    void isUserAdmin_failure() {
        User user1 = new User("1", "John Doe", "User");
        User user2 = new User("1", "John Doe", "Admin");


        assertFalse(user1.isUserAdmin());
    }

    @Test
    void isUserAdmin_success() {
        User user1 = new User("1", "John Doe", "User");
        User user2 = new User("1", "John Doe", "Admin");


        assertTrue(user2.isUserAdmin());
    }
}