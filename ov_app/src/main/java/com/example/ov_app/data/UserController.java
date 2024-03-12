package com.example.ov_app.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class UserController {
    private static final ArrayList<User> users = new ArrayList<>();
    static {
        User user1 = new User("Kees", 1234);
        User user2 = new User("Rens", 1234);
        User user3 = new User("Gerco", 1234);
        User user4 = new User("Atilla", 1234);
        User user5 = new User("Jeroen", 1234);

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
    }
    public static void main(String[] args) {
        File routesFile = new File("users.json");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        // enable indentation in outputted JSON files
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            mapper.writeValue(routesFile, users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkIfUserExists(String username){
        ArrayList<String> users_usernames = new ArrayList<>();
        for(User user : users){
            users_usernames.add(user.getUsername());
        }
        return users_usernames.contains(username);
    }

    public static boolean checkUserCredentials(String username, String password){
        for(User user : users){
            if(user.getUsername().equals(username) && user.getPassword() == parseInt(password)){
                return true;
            }
        }
        return false;
    }
}
