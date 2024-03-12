package com.example.ov_app;

import com.example.ov_app.data.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class StatusController implements Initializable {

    @FXML public Pane pane;
    @FXML public Button logoutButton;
    @FXML public Label currentUserLabel;
    @FXML public Label usernameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserData.getInstance().currentUserProperty().addListener((observable, oldValue, newValue) -> {
            usernameLabel.setText(newValue.getUsername());
            pane.setVisible(!UserData.getInstance().getUnsignedUser().equals(newValue));
        });
    }

    public void onLogoutButton(ActionEvent actionEvent) {
        UserData.getInstance().logout();
    }
}
