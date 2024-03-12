package com.example.ov_app.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserModel {

    private final StringProperty username;
    private final StringProperty password;
    private final ObservableList<StationPair> history;
    private final ObservableList<StationPair> favorites;

    public UserModel(SerializableUserModel serializableUserModel) {
        this.username = new SimpleStringProperty(serializableUserModel.getUsername());
        this.password = new SimpleStringProperty(serializableUserModel.getPassword());
        this.history = FXCollections.observableArrayList(serializableUserModel.getHistory());
        this.favorites = FXCollections.observableArrayList(serializableUserModel.getFavorites());
    }

    public UserModel(String username, String password) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.history = FXCollections.observableArrayList();
        this.favorites = FXCollections.observableArrayList();
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public ObservableList<StationPair> getHistory() {
        return history;
    }

    public ObservableList<StationPair> getFavorites() {
        return favorites;
    }
}
