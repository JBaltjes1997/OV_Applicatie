package com.example.ov_app;

import java.util.Locale;
import java.util.ResourceBundle;

public class Translator {
    private static ResourceBundle messages;

    public static void setLanguage(String language) {
        try {
            Locale locale = new Locale(language);
            messages = ResourceBundle.getBundle("Messsages", locale);
        } catch(Exception e){
            System.out.println(e);
        }
    }

    public static String translate(String key) {
        try {
            return messages.getString(key);
        } catch (Exception e) {
            System.out.println("Key not found");
        }
        return key;
    }
}
