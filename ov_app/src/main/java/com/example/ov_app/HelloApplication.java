package com.example.ov_app;

import com.example.ov_app.data.UserData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        UserData.getInstance().loadData();
        stage.setOnCloseRequest(windowEvent -> {
            UserData.getInstance().saveData();
        });

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);

        HelloController controller = fxmlLoader.getController();

        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/com/example/image/icon.jpg"))));
        stage.setTitle("OV-App!");
        stage.setScene(scene);

        PKBoard pkBoard = new PKBoard(controller);


        scene.addEventFilter(KeyEvent.KEY_PRESSED, pkBoard::pb_Down);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, pkBoard::pb_Left);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, pkBoard::pb_Right);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, pkBoard::pb_Up);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, controller::handleKeyPressed);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}