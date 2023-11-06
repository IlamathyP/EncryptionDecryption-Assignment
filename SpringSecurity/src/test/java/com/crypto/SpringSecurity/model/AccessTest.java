package com.crypto.SpringSecurity.model;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AccessTest {


    @Test
    void doesUserHasAccessToApp_success() {
        User user1 = new User("1", "John Doe", "User");
        Access access = new Access("1","App1",user1,"QXBwMQ==");
        assertTrue(access.doesUserHasAccessToApp(access.getAppName(),access.getUser().getUserId(), "QXBwMQ=="));

    }

    @Test
    void doesUserHasAccessToAppWithWrongAPIKey_failure(){
        User user1 = new User("1", "John Doe", "User");
        Access access = new Access("1","App1",user1,"QXBwMQ==");
        assertFalse(access.doesUserHasAccessToApp(access.getAppName(),access.getUser().getUserId(), "QXBwhgfhgfMg"));
    }

    @Test
    void doesUserHasAccessToAppWithWrongAppId_failure(){
        User user1 = new User("1", "John Doe", "User");
        Access access = new Access("1","App1",user1,"QXBwMQ==");
        assertFalse(access.doesUserHasAccessToApp("App2",access.getUser().getUserId(), "QXBwMQ=="));
    }
    @Test
    void doesUserHasAccessToAppWithWrongAppName_failure(){
        User user1 = new User("1", "John Doe", "User");
        Access access = new Access("1","App1",user1,"QXBwMQ==");
        assertFalse(access.doesUserHasAccessToApp("App3","1", "QXBwMQ=="));
    }




}