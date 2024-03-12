package com.example.ov_app.data;

public class User {
    String username;
    int password;

    public User(String username, int password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public int getPassword() {
        return password;
    }
}
