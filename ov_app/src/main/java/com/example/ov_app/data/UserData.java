package com.example.ov_app.data;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TODO maybe add guard clauses to methods checking whether data is loaded and throw an exception if not
public class UserData {

    private static final File USERS_FILE = new File("users.json");
    private static final UserData instance = new UserData();
    private final UserModel unsignedUser = new UserModel("", "");
    private final List<UserModel> users = new ArrayList<>();
    private final ObjectProperty<UserModel> currentUser = new SimpleObjectProperty<>();

    // singleton
    private UserData() {
        users.add(unsignedUser);
        setCurrentUser(unsignedUser);
    }

    public static UserData getInstance() {
        return instance;
    }

    public void loadData() {
        System.out.println("Loading data...");
        ObjectMapper mapper = createMapper();
        try {
            List<SerializableUserModel> userData = mapper.readValue(USERS_FILE,
                    mapper.getTypeFactory().constructCollectionType(List.class, SerializableUserModel.class));
            userData.stream()
                    .map(UserModel::new)
                    .forEach(users::add);
        } catch (IOException e) {
            System.out.println("Data not found, creating default users.");
            System.out.println(e.getMessage());
            users.addAll(getDefaultUsers());
        }
    }

    public void saveData() {
        System.out.println("Saving data...");
        ObjectMapper mapper = createMapper();
        try {
            List<SerializableUserModel> userData = users.stream()
                    .filter(userModel -> !userModel.equals(unsignedUser))
                    .map(SerializableUserModel::new)
                    .toList();
            mapper.writeValue(USERS_FILE, userData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void login(String username, String password) throws InvalidCredentialsException {
        UserModel user = users.stream()
                .filter(userModel -> userModel.getUsername().equals(username))
                .filter(userModel -> userModel.getPassword().equals(password))
                .findAny()
                .orElseThrow(InvalidCredentialsException::new);
        setCurrentUser(user);
    }

    public void logout() {
        setCurrentUser(unsignedUser);
    }

    private ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // we need this to serialize and parse LocalDate/LocalDate java8 objects
        mapper.registerModule(new JavaTimeModule());
        // we need this to ignore type information when deserializing
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // enable indentation in outputted JSON files
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }

    private List<UserModel> getDefaultUsers() {
        List<UserModel> users = new ArrayList<>();
        users.add(new UserModel("Kees", "1234"));
        users.add(new UserModel("Rens", "1234"));
        users.add(new UserModel("Gerco", "1234"));
        users.add(new UserModel("Atilla", "1234"));
        users.add(new UserModel("Jeroen", "1234"));
        return users;
    }

    public UserModel getUnsignedUser() {
        return unsignedUser;
    }

    public UserModel getCurrentUser() {
        return currentUser.get();
    }

    public ObjectProperty<UserModel> currentUserProperty() {
        return currentUser;
    }

    public void setCurrentUser(UserModel currentUser) {
        this.currentUser.set(currentUser);
    }
}