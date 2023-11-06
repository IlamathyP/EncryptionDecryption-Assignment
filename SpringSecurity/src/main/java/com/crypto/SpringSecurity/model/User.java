package com.crypto.SpringSecurity.model;

public class User {
    private String userId;
    private String userName;
    private String role;

    public User(String userId, String userName, String role) {
        this.userId = userId;
        this.userName = userName;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public boolean isUserAdmin(){
        return getRole().equalsIgnoreCase("Admin");
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
