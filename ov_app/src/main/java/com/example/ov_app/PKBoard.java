package com.example.ov_app;

import javafx.event.Event;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class PKBoard {

    private final HelloController helloController;

    public PKBoard(HelloController helloController) {
        this.helloController = helloController;
    }

    public void pb_Down(KeyEvent event) {
        if (!helloController.isPKBoardEnabled()) {
            return;
        }
        if (event.getCode() == KeyCode.C ||event.getCode() == KeyCode.V || event.getCode() == KeyCode.B || event.getCode() == KeyCode.N|| event.getCode() == KeyCode.M|| event.getCode() == KeyCode.SPACE) {
            // Simulate a Tab key press
            KeyEvent tabEvent = new KeyEvent(
                    KeyEvent.KEY_PRESSED,
                    " ",
                    " ",
                    KeyCode.DOWN,
                    false,
                    false,
                    false,
                    false
            );
            Event.fireEvent(event.getTarget(), tabEvent);
            event.consume();
        }
    }

    public void pb_Up(KeyEvent event) {
        if (!helloController.isPKBoardEnabled()) {
            return;
        }
        if (event.getCode() == KeyCode.R ||event.getCode() == KeyCode.T || event.getCode() == KeyCode.Y || event.getCode() == KeyCode.U|| event.getCode() == KeyCode.I|| event.getCode() == KeyCode.DIGIT4|| event.getCode() == KeyCode.DIGIT5|| event.getCode() == KeyCode.DIGIT6|| event.getCode() == KeyCode.DIGIT7|| event.getCode() == KeyCode.DIGIT8|| event.getCode() == KeyCode.DIGIT9) {
            // Simulate a Tab key press
            KeyEvent tabEvent = new KeyEvent(
                    KeyEvent.KEY_PRESSED,
                    " ",
                    " ",
                    KeyCode.UP,
                    false,
                    false,
                    false,
                    false
            );
            Event.fireEvent(event.getTarget(), tabEvent);
            event.consume();
        }
    }

    public void pb_Left(KeyEvent event) {
        if (!helloController.isPKBoardEnabled()) {
            return;
        }
        if (event.getCode() == KeyCode.Q || event.getCode() == KeyCode.Q || event.getCode() == KeyCode.DIGIT1 ||event.getCode() == KeyCode.DIGIT2 || event.getCode() == KeyCode.W|| event.getCode() == KeyCode.CAPS|| event.getCode() == KeyCode.A|| event.getCode() == KeyCode.S|| event.getCode() == KeyCode.D|| event.getCode() == KeyCode.SHIFT|| event.getCode() == KeyCode.Z|| event.getCode() == KeyCode.WINDOWS|| event.getCode() == KeyCode.CONTROL) {
            // Simulate a Tab key press ||
            KeyEvent tabEvent = new KeyEvent(
                    KeyEvent.KEY_PRESSED,
                    " ",
                    " ",
                    KeyCode.LEFT,
                    false,
                    false,
                    false,
                    false
            );
            Event.fireEvent(event.getTarget(), tabEvent);
            event.consume();
        }
    }

    public void pb_Right(KeyEvent event) {
        if (!helloController.isPKBoardEnabled()) {
            return;
        }
        if (event.getCode() == KeyCode.MINUS || event.getCode() == KeyCode.MINUS || event.getCode() == KeyCode.PLUS || event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.P || event.getCode() == KeyCode.OPEN_BRACKET || event.getCode() == KeyCode.CLOSE_BRACKET || event.getCode() == KeyCode.BRACELEFT || event.getCode() == KeyCode.BRACERIGHT || event.getCode() == KeyCode.L || event.getCode() == KeyCode.SEMICOLON || event.getCode() == KeyCode.QUOTE || event.getCode() == KeyCode.PERIOD || event.getCode() == KeyCode.SLASH || event.getCode() == KeyCode.BACK_SLASH) {
            // Simulate a Tab key press
            KeyEvent tabEvent = new KeyEvent(
                    KeyEvent.KEY_PRESSED,
                    " ",
                    " ",
                    KeyCode.RIGHT,
                    false,
                    false,
                    false,
                    false
            );
            Event.fireEvent(event.getTarget(), tabEvent);
            event.consume();
        }
    }

    private void handleMousePressLeftClickToTab(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            // Simulate a Tab key press
            KeyEvent tabEvent = new KeyEvent(
                    KeyEvent.KEY_PRESSED,
                    " ",
                    " ",
                    KeyCode.TAB,
                    false,
                    false,
                    false,
                    false
            );
            Event.fireEvent(event.getTarget(), tabEvent);
            event.consume();
        }
    }


    private void handleMousePressRightClickToEnter(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            // Simulate a Tab key press
            KeyEvent tabEvent = new KeyEvent(
                    KeyEvent.KEY_PRESSED,
                    " ",
                    " ",
                    KeyCode.ENTER,
                    false,
                    false,
                    false,
                    false
            );
            Event.fireEvent(event.getTarget(), tabEvent);
            event.consume();
        }
    }

    /*
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.L || event.getCode() == KeyCode.Q) {
            // Simulate a Tab key press
            KeyEvent tabEvent = new KeyEvent(
                    KeyEvent.KEY_PRESSED,
                    " ",
                    " ",
                    KeyCode.TAB,
                    false,
                    false,
                    false,
                    false
            );
            Event.fireEvent(event.getTarget(), tabEvent);
            event.consume();
        }
    }
    */
}
