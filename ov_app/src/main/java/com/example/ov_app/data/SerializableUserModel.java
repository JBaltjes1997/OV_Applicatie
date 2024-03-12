package com.example.ov_app.data;

import java.util.List;

public class SerializableUserModel {

    private String username;
    private String password;
    private List<StationPair> history;
    private List<StationPair> favorites;

    // for jackson
    public SerializableUserModel() {}

    public SerializableUserModel(UserModel userModel) {
        this.username = userModel.getUsername();
        this.password = userModel.getPassword();
        this.history = userModel.getHistory().stream().toList();
        this.favorites = userModel.getFavorites().stream().toList();
    }

    public SerializableUserModel(String username, String password, List<StationPair> history, List<StationPair> favorites) {
        this.username = username;
        this.password = password;
        this.history = history;
        this.favorites = favorites;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<StationPair> getHistory() {
        return history;
    }

    public void setHistory(List<StationPair> history) {
        this.history = history;
    }

    public List<StationPair> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<StationPair> favorites) {
        this.favorites = favorites;
    }
}
