package com.example.ov_app;

import com.example.ov_app.data.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML public Pane myPaneRightFavorites;
    @FXML public Pane myPaneRightHistory;
    @FXML public Pane myPaneMiddle;
    @FXML public Pane paneLeft;
    @FXML public Pane currentlyFocusedPane;

    @FXML public Label departureLabel;
    @FXML public Label arrivalLabel;
    @FXML public Label arrivalPlatformLabel;
    @FXML public Label departurePlatformLabel;
    @FXML public Label departureStationLabel;
    @FXML public Label arrivalStationLabel;
    @FXML public Label travelTimeLabel;
    @FXML public Label stationsLabel;
    @FXML public Label travelTimeTextLabel;
    @FXML public Label arrivalTimeLabel;
    @FXML public Label departureTimeLabel;
    @FXML public TableView<Node> stationsTable;
    @FXML public ListView<StationPair> historyListView;
    @FXML public StatusController statusController;
    @FXML public Pane journeyInfoPane;
    @FXML
    private Label travelPlanner, travelAdvice, travelHistory, favorites, time, currentTime;

    @FXML
    private CheckBox PK_OnOff;

    @FXML
    private Button loginButton, planJourney, buttonSwitchArrow, favoriteButton, DutchFlag, BrittishFlag;

    @FXML
    private Spinner<Integer> spinnerHour, spinnerMinute = new Spinner<>();

    @FXML
    private ComboBox<String> comboBoxDeparture, comboBoxArrival;

    @FXML
    private TextField loginTextFieldUsername, loginTextFieldPassword;

    @FXML
    private Label errorMessage, unregisteredUserMessage;

    @FXML
    Button infoPopUp;

    @FXML
    private RadioButton buttonBus, buttonTrain;
    private boolean isButtonFilled = false;
    @FXML
    private ListView<StationPair> favoritesList;
    private Stage stage;
    @FXML
    private Scene scene;
    private Parent root;
   // public Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));

    @FXML
    private void showPopup() {
        // Create the popup
        Popup popup = new Popup();
        Image infoImage = new Image(String.valueOf(getClass().getResource("/com/example/image/Keyboard_OV_App.png")));
        ImageView imageView = new ImageView(infoImage);
        HBox hbox = new HBox(10); // 10 is the spacing between images
        //hbox.getChildren().addAll(imageView);
        imageView.setFitWidth(500);
        imageView.setFitHeight(330);

        Label textLabel = new Label("\n Beste reizigers, \n" +
                "\n" +
                "\n" +
                "Welkom in het gebruik van onze applicatie waar u op een gemakkelijke manier uw reizen in het openbaar vervoer kunt plannen.  \n" +
                "\n" +
                "\n" +
                "Onze applicatie is te gebruiken zonder dat er precieze bewegingen met een muis noodzakelijk zijn. \n" +
                "\n" +
                "Daarom hebben we het toetsenbord opgedeeld in vier delen die de werking van de pijltjestoetsen uitvoeren. \n" +
                "\n" +
                "De linker-muisknop gebruikt u om door de applicatie heen te navigeren en de rechter-muisknop om een keuze te bevestigen. \n" +
                "\n" +
                "\n" +
                "Wij wensen u veel reisplezier! \n" +
                "\n" +
                        "  \n" +
                        "\n" );

        // Set style if needed
        textLabel.setStyle("-fx-font-size: 18; -fx-padding: 6px;");

        // Create a StackPane to hold the text label (you can use other layouts if needed)
        StackPane content = new StackPane();
        content.setStyle("-fx-background-color: red;");
        content.getChildren().add(textLabel);
        //=content.getChildren().add(imageView);
        VBox vbox = new VBox(textLabel);
        VBox vbox2 = new VBox(imageView);
        vbox2.setLayoutX(690);
        vbox2.setLayoutY(345);
        //popup.getContent().add(content);
        vbox2.setAlignment(Pos.BOTTOM_RIGHT);
        popup.getContent().add(vbox);
        popup.getContent().add(vbox2);
        textLabel.setPrefWidth(1200);
        textLabel.setPrefHeight(700);

        vbox.setAlignment(Pos.TOP_LEFT);
        textLabel.setAlignment(Pos.TOP_LEFT);

       // vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: lightblue;");
        // Set auto-hide (optional)
        popup.setAutoHide(true);

        popup.show(infoPopUp.getScene().getWindow());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Translator.setLanguage("nl");
        SetSwitchButton();
        SetBusButton();
        SetTrainButton();
        setCurrentTime();
        updateUI();
        initializePKBoard();

        buttonBus.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                buttonBus.setStyle("-fx-cursor: hand; -fx-focus-color: blue;");
                buttonTrain.setStyle("-fx-cursor: hand; -fx-focus-color: blue; ");
                PK_OnOff.setStyle("-fx-cursor: hand; -fx-focus-color: blue;");
                loginButton.setStyle("-fx-cursor: hand; -fx-focus-color: blue;");
                planJourney.setStyle("-fx-cursor: hand; -fx-focus-color: blue;");
                comboBoxArrival.setStyle("-fx-cursor: hand; -fx-focus-color: blue;");
                comboBoxDeparture.setStyle("-fx-cursor: hand; -fx-focus-color: blue;");
                infoPopUp.setStyle("-fx-cursor: hand; -fx-focus-color: blue;");
                buttonSwitchArrow.setStyle("-fx-cursor: hand; -fx-focus-color: blue;");
                DutchFlag.setStyle("-fx-cursor: hand; -fx-focus-color: blue;");
                BrittishFlag.setStyle("-fx-cursor: hand; -fx-focus-color: blue;");
                infoPopUp.setStyle("-fx-cursor: hand; -fx-focus-color: blue;");
//                favoriteButton.setStyle("-fx-cursor: hand; -fx-background-color: transparent;");
            }
        });

        // fill departure/destination combo boxes
        populateCombos(null);

        // hour/minute spinners setup
        setupSpinners();

        // disable plan journey button unless departure/destination are selected
        planJourney.disableProperty().bind(comboBoxArrival.valueProperty().isNull()
                .or(comboBoxDeparture.valueProperty().isNull()));

        // connect history list view to userdata
        historyListView.setItems(UserData.getInstance().getCurrentUser().getHistory());
        UserData.getInstance().currentUserProperty().addListener(((observable, oldValue, newValue) -> {
            historyListView.setItems(newValue.getHistory());
        } ));

        // connect favorites list view to userdata
        favoritesList.setItems(UserData.getInstance().getCurrentUser().getFavorites());
        UserData.getInstance().currentUserProperty().addListener(((observable, oldValue, newValue) -> {
            favoritesList.setItems(newValue.getFavorites());
        } ));

        // disable login button when username and password fields are empty
        loginButton.disableProperty().bind(
                Bindings.isEmpty(loginTextFieldUsername.textProperty())
                        .or(Bindings.isEmpty(loginTextFieldPassword.textProperty()))
        );

        // plan set departure/arrival locations in combo boxes and plan journey when clicking a history item
        historyListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!comboBoxDeparture.getItems().contains(newValue.getDepartureLocation())) {
                    // switch train/bus
                    if (buttonTrain.isSelected()) {
                        buttonBus.setSelected(true);
                    } else {
                        buttonTrain.setSelected(true);
                    }
                    populateCombos(null);
                }
                comboBoxDeparture.getSelectionModel().select(newValue.getDepartureLocation());
                onComboAction(null);
                comboBoxArrival.getSelectionModel().select(newValue.getDestination());
                planJourney.fire();
            }
        });

        // plan set departure/arrival locations in combo boxes and plan journey when clicking a favorite item
        favoritesList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!comboBoxDeparture.getItems().contains(newValue.getDepartureLocation())) {
                    // switch train/bus
                    if (buttonTrain.isSelected()) {
                        buttonBus.setSelected(true);
                    } else {
                        buttonTrain.setSelected(true);
                    }
                    populateCombos(null);
                }
                comboBoxDeparture.getSelectionModel().select(newValue.getDepartureLocation());
                onComboAction(null);
                comboBoxArrival.getSelectionModel().select(newValue.getDestination());
                planJourney.fire();
            }
        });

        // clear selection from favorites/history/stations when focus is lost
        historyListView.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                historyListView.getSelectionModel().clearSelection();
            }
        });
        favoritesList.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                favoritesList.getSelectionModel().clearSelection();
            }
        });
        stationsTable.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                stationsTable.getSelectionModel().clearSelection();
            }
        });

        // hide journey info UI at the start
        journeyInfoPane.setVisible(false);

        // switch to password field when pressing enter in username field
        loginTextFieldUsername.setOnKeyPressed( event -> {
            if( event.getCode() == KeyCode.ENTER ) {
                loginTextFieldPassword.requestFocus();
            }
        } );

        // fire login button when pressing enter inside the password field
        loginTextFieldPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && !loginButton.isDisabled()) {
                loginButton.fire();
            }
        });
    }

    public void initializePKBoard() {
        // Set focus on pane1 when the application starts
        buttonTrain.requestFocus();
        currentlyFocusedPane = paneLeft;
        System.out.println(currentlyFocusedPane.toString());
    }

    @FXML
    void handleKeyPressed(KeyEvent event) {
        if (!isPKBoardEnabled()) {
            return;
        }
        BackgroundFill backgroundFilldefault = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY);
        BackgroundFill backgroundFillnew = new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY);
        Background backgroundnew = new Background(backgroundFillnew);
        Background backgrounddefault = new Background(backgroundFilldefault);

        if (currentlyFocusedPane == paneLeft) {
            switch (event.getCode()) {
                case D:
                    // Move focus to pane2 when the right arrow key is pressed

                    //tripsInfo.requestFocus();
                    journeyInfoPane.requestFocus();

                    currentlyFocusedPane = myPaneMiddle;
                    System.out.println(currentlyFocusedPane.toString());
                 /*   Event.fireEvent(event.getTarget(), event);
                    event.consume();*/
                    paneLeft.setBackground(backgrounddefault);
                    myPaneMiddle.setBackground(backgroundnew);
                    myPaneRightHistory.setBackground(backgrounddefault);
                    myPaneRightFavorites.setBackground(backgrounddefault);

                    paneLeft.setPrefSize(362/1.03,634/1.03);
                    myPaneMiddle.setPrefSize(362*1.03,634*1.03);
                    myPaneRightHistory.setPrefSize(340/1.03,326/1.03);
                    myPaneRightFavorites.setPrefSize(340/1.03,296/1.03);

                    break;
                case A:
                    // Move focus back to pane1 when the left arrow key is pressed
                    buttonTrain.requestFocus();
                    currentlyFocusedPane = paneLeft;
                    System.out.println(currentlyFocusedPane.toString());
                /*    Event.fireEvent(event.getTarget(), event);
                    event.consume();*/
                    paneLeft.setBackground(backgroundnew);
                    myPaneMiddle.setBackground(backgrounddefault);
                    myPaneRightHistory.setBackground(backgrounddefault);
                    myPaneRightFavorites.setBackground(backgrounddefault);

                    paneLeft.setPrefSize(362*1.03,634*1.03);
                    myPaneMiddle.setPrefSize(362/1.03,634/1.03);
                    myPaneRightHistory.setPrefSize(340/1.03,326/1.03);
                    myPaneRightFavorites.setPrefSize(340/1.03,296/1.03);
                    break;
                case S:
                    //||event.getCode() == KeyCode.V || event.getCode() == KeyCode.B || event.getCode() == KeyCode.N|| event.getCode() == KeyCode.M|| event.getCode() == KeyCode.SPACE
                    // Simulate a Bottom Arrow key press
                    KeyEvent tabEvent = new KeyEvent(
                            KeyEvent.KEY_PRESSED,
                            " ",
                            " ",
                            KeyCode.TAB,
                            true,
                            false,
                            false,
                            false
                    );
                    Event.fireEvent(event.getTarget(), tabEvent);
                    event.consume();
                    break;
                // Add more cases for other arrow keys if needed
            }
        } else if (currentlyFocusedPane == myPaneMiddle) {
            switch (event.getCode()) {
                case A:
                    // Move focus to pane2 when the right arrow key is pressed
                    buttonBus.requestFocus();
                    currentlyFocusedPane = paneLeft;
                    System.out.println(currentlyFocusedPane.toString());
                   /* Event.fireEvent(event.getTarget(), event);
                    event.consume();*/
                    paneLeft.setBackground(backgroundnew);
                    myPaneMiddle.setBackground(backgrounddefault);
                    myPaneRightHistory.setBackground(backgrounddefault);
                    myPaneRightFavorites.setBackground(backgrounddefault);

                    paneLeft.setPrefSize(362*1.03,634*1.03);
                    myPaneMiddle.setPrefSize(362/1.03,634/1.03);
                    myPaneRightHistory.setPrefSize(340/1.03,326/1.03);
                    myPaneRightFavorites.setPrefSize(340/1.03,296/1.03);
                    break;
                case D:
                    // Move focus back to pane1 when the left arrow key is pressed
                    historyListView.requestFocus();
                    currentlyFocusedPane = myPaneRightHistory;
                    System.out.println(currentlyFocusedPane.toString());
              /*      Event.fireEvent(event.getTarget(), event);
                    event.consume();*/
                    paneLeft.setBackground(backgrounddefault);
                    myPaneMiddle.setBackground(backgrounddefault);
                    myPaneRightHistory.setBackground(backgroundnew);
                    myPaneRightFavorites.setBackground(backgrounddefault);

                    paneLeft.setPrefSize(362/1.03,634/1.03);
                    myPaneMiddle.setPrefSize(362/1.03,634/1.03);
                    myPaneRightHistory.setPrefSize(340*1.03,326*1.03);
                    myPaneRightFavorites.setPrefSize(340/1.03,296/1.03);
                    break;
            }
        }
        else if (currentlyFocusedPane == myPaneRightHistory) {

            switch (event.getCode()) {
                case A:
                    // Move focus to pane2 when the right arrow key is pressed

                    //tripsInfo.requestFocus();
                    journeyInfoPane.requestFocus();

                    currentlyFocusedPane = myPaneMiddle;
                    System.out.println(currentlyFocusedPane.toString());
                   /* Event.fireEvent(event.getTarget(), event);
                    event.consume();*/
                    paneLeft.setBackground(backgrounddefault);
                    myPaneMiddle.setBackground(backgroundnew);
                    myPaneRightHistory.setBackground(backgrounddefault);
                    myPaneRightFavorites.setBackground(backgrounddefault);

                    paneLeft.setPrefSize(362/1.03,634/1.03);
                    myPaneMiddle.setPrefSize(362*1.03,634*1.03);
                    myPaneRightHistory.setPrefSize(340/1.03,326/1.03);
                    myPaneRightFavorites.setPrefSize(340/1.03,296/1.03);
                    break;
                case S:
                    // Move focus back to pane1 when the left arrow key is pressed
                    favoritesList.requestFocus();
                    currentlyFocusedPane = myPaneRightFavorites;
                    System.out.println(currentlyFocusedPane.toString());
              /*      Event.fireEvent(event.getTarget(), event);
                    event.consume();*/
                    paneLeft.setBackground(backgrounddefault);
                    myPaneMiddle.setBackground(backgrounddefault);
                    myPaneRightHistory.setBackground(backgrounddefault);
                    myPaneRightFavorites.setBackground(backgroundnew);

                    paneLeft.setPrefSize(362/1.03,634/1.03);
                    myPaneMiddle.setPrefSize(362/1.03,634/1.03);
                    myPaneRightHistory.setPrefSize(340/1.03,326/1.03);
                    myPaneRightFavorites.setPrefSize(340*1.03,296*1.03);
                    break;
            }
        }

        else if (currentlyFocusedPane == myPaneRightFavorites) {

            paneLeft.setPrefSize(362/1.03,634/1.03);
            myPaneMiddle.setPrefSize(362/1.03,634/1.03);
            myPaneRightHistory.setPrefSize(340/1.03,326/1.03);
            myPaneRightFavorites.setPrefSize(340*1.03,296*1.03);
            switch (event.getCode()) {
                case W:
                    // Move focus to pane2 when the right arrow key is pressed
                    historyListView.requestFocus();
                    currentlyFocusedPane = myPaneRightHistory;
                    System.out.println(currentlyFocusedPane.toString());
                   /* Event.fireEvent(event.getTarget(), event);
                    event.consume();*/
                    paneLeft.setBackground(backgrounddefault);
                    myPaneMiddle.setBackground(backgrounddefault);
                    myPaneRightHistory.setBackground(backgroundnew);
                    myPaneRightFavorites.setBackground(backgrounddefault);

                    paneLeft.setPrefSize(362/1.03,634/1.03);
                    myPaneMiddle.setPrefSize(362/1.03,634/1.03);
                    myPaneRightHistory.setPrefSize(340*1.03,326*1.03);
                    myPaneRightFavorites.setPrefSize(340/1.03,296/1.03);
                    break;
                case A:
                    // Move focus back to pane1 when the left arrow key is pressed

                    //tripsInfo.requestFocus();
                    journeyInfoPane.requestFocus();

                    currentlyFocusedPane = myPaneMiddle;
                    System.out.println(currentlyFocusedPane.toString());
              /*      Event.fireEvent(event.getTarget(), event);
                    event.consume();*/
                    paneLeft.setBackground(backgrounddefault);
                    myPaneMiddle.setBackground(backgroundnew);
                    myPaneRightHistory.setBackground(backgrounddefault);
                    myPaneRightFavorites.setBackground(backgrounddefault);

                    paneLeft.setPrefSize(362/1.03,634/1.03);
                    myPaneMiddle.setPrefSize(362*1.03,634*1.03);
                    myPaneRightHistory.setPrefSize(340/1.03,326/1.03);
                    myPaneRightFavorites.setPrefSize(340/1.03,296/1.03);
                    break;
            }
        }
    }

    private void setupSpinners() {
        // set current time in spinners
        LocalTime now = LocalTime.now();
        spinnerHour.getValueFactory().setValue(now.getHour());
        spinnerMinute.getValueFactory().setValue(now.getMinute());

        // setup hour spinner
        spinnerHour.editorProperty().get().setAlignment(Pos.CENTER);
        spinnerHour.getValueFactory().setWrapAround(true);
        // enable scroll on spinner
        spinnerHour.setOnScroll(event -> {
            if (event.getDeltaY() > 0) {
                spinnerHour.decrement();
            } else if (event.getDeltaY() < 0) {
                spinnerHour.increment();
            }
        });
        // the spinner cannot be empty, instead select the current value and overwrite it
        spinnerHour.getEditor().setOnKeyPressed(k -> {
            if (k.getCode().equals(KeyCode.BACK_SPACE)) {
                Platform.runLater(spinnerHour.getEditor()::selectAll);
            }
        });
        // select all text when gaining focus, so you can tab into the spinner and edit right away
        spinnerHour.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            if (isFocused) {
                Platform.runLater(spinnerHour.getEditor()::selectAll);
            }
        });
        // set limits 0-23 minutes on spinner editor
        spinnerHour.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                spinnerHour.getEditor().setText(oldValue);
            } else {
                if (newValue.length() > 2) {
                    spinnerHour.getEditor().setText(oldValue);
                }
                try {
                    int value = Integer.parseInt(newValue);
                    if (value > 23) {
                        spinnerHour.getEditor().setText(oldValue);
                    }
                    if (value < 0) {
                        spinnerHour.getEditor().setText(oldValue);
                    }
                } catch (NumberFormatException e) {
                    spinnerHour.getEditor().setText(oldValue);
                }
            }
        });

        spinnerMinute.editorProperty().get().setAlignment(Pos.CENTER);
        spinnerMinute.getValueFactory().setWrapAround(true);
        // enable scroll on spinner
        spinnerMinute.setOnScroll(event -> {
            if (event.getDeltaY() > 0) {
                spinnerMinute.decrement();
            } else if (event.getDeltaY() < 0) {
                spinnerMinute.increment();
            }
        });
        // the spinner cannot be empty, instead select the current value and overwrite it
        spinnerMinute.getEditor().setOnKeyPressed(k -> {
            if (k.getCode().equals(KeyCode.BACK_SPACE)) {
                Platform.runLater(spinnerMinute.getEditor()::selectAll);
            }
        });
        // select all text when gaining focus, so you can tab into the spinner and edit right away
        spinnerMinute.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            if (isFocused) {
                Platform.runLater(spinnerMinute.getEditor()::selectAll);
            }
        });
        // set limits 0-59 minutes on spinner editor
        spinnerMinute.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                spinnerMinute.getEditor().setText(oldValue);
            } else {
                if (newValue.length() > 2) {
                    spinnerMinute.getEditor().setText(oldValue);
                }
                try {
                    int value = Integer.parseInt(newValue);
                    if (value > 59) {
                        spinnerMinute.getEditor().setText(oldValue);
                    }
                    if (value < 0) {
                        spinnerMinute.getEditor().setText(oldValue);
                    }
                } catch (NumberFormatException e) {
                    spinnerMinute.getEditor().setText(oldValue);
                }
            }
        });

        // reset invalid username message when username/password fields change
        loginTextFieldPassword.textProperty().addListener(((observable, oldValue, newValue) ->
                unregisteredUserMessage.setText("")));
        loginTextFieldUsername.textProperty().addListener(((observable, oldValue, newValue) ->
                unregisteredUserMessage.setText("")));

        new AutoCompleteComboBoxListener<>(comboBoxDeparture);
        new AutoCompleteComboBoxListener<>(comboBoxArrival);
    }

    private void favoritesButton() {
        StationPair stationPair = new StationPair(departureStationLabel.getText(), arrivalStationLabel.getText());
        ObservableList<StationPair> favorites = favoritesList.getItems();

        if (favorites.contains(stationPair)) {
            setButtonImage(favoriteButton, "heart2.jpg");
            isButtonFilled = true;
        } else {
            setButtonImage(favoriteButton, "heart1.jpg");
            isButtonFilled = false;
        }
        favoriteButton.setOnAction(e -> handleButtonClick(favoriteButton));
    }

    public void handleButtonClick(Button favoriteButton){
        if(isButtonFilled) {
            setButtonImage(favoriteButton, "heart1.jpg");
            removeItemFavoriteList();
            isButtonFilled = false;
        }else{
            setButtonImage(favoriteButton, "heart2.jpg");
            addItemFavoriteList();
            isButtonFilled = true;
        }
    }

    private void addItemFavoriteList() {
        StationPair stationPair = new StationPair(departureStationLabel.getText(), arrivalStationLabel.getText());
        ObservableList<StationPair> favorites = favoritesList.getItems();
        if (!favorites.contains(stationPair)) {
            favorites.add(stationPair);
        }
    }

    private void removeItemFavoriteList() {
        // if we don't do this first it might automatically select the next option, which we do not want unless the user
        // explicitly selects it (because it will plan the journey upon selection)
        favoritesList.getSelectionModel().clearSelection();

        StationPair stationPair = new StationPair(departureStationLabel.getText(), arrivalStationLabel.getText());
        favoritesList.getItems().remove(stationPair);
    }

    public void setButtonImage(Button favoriteButton, String heart) {
        favoriteButton.setStyle("-fx-background-color: transparent;");
        Image starImage = new Image(String.valueOf(getClass().getResource("/com/example/image/" + heart)));
        ImageView starImageView = new ImageView(starImage);

        favoriteButton.setGraphic(starImageView);
    }

    public void SetSwitchButton() {
        buttonSwitchArrow.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        buttonSwitchArrow.setPadding(Insets.EMPTY);
    }

    public void switchDestination() {
        String comboBoxVertrekValue = comboBoxDeparture.getValue();
        String comboBoxAankomstValue = comboBoxArrival.getValue();
        comboBoxDeparture.setValue(comboBoxAankomstValue);
        comboBoxArrival.setValue(comboBoxVertrekValue);
    }

    public void SetBusButton() {
        buttonBus.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        buttonBus.getStyleClass().remove("radio-button");
        buttonBus.getStyleClass().add("toggle-button");
        Image img = new Image(String.valueOf(getClass().getResource("/com/example/image/Bus.jpg")));
        ImageView view = new ImageView(img);
        buttonBus.setGraphic(view);
    }

    public void SetTrainButton() {
        buttonTrain.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        buttonTrain.getStyleClass().remove("radio-button");
        buttonTrain.getStyleClass().add("toggle-button");
        Image img = new Image(String.valueOf(getClass().getResource("/com/example/image/train.jpg")));
        ImageView view = new ImageView(img);
        buttonTrain.setGraphic(view);
    }

    public void setCurrentTime() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                currentTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))
        ),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void updateUI() {
        time.setText(Translator.translate("time"));
        loginButton.setText(Translator.translate("loginButton"));
        planJourney.setText(Translator.translate("planJourney"));
        travelPlanner.setText(Translator.translate("travelPlanner"));
        travelAdvice.setText(Translator.translate("travelAdvice"));
        travelHistory.setText(Translator.translate("travelHistory"));
        favorites.setText(Translator.translate("favorites"));
        loginTextFieldUsername.setPromptText(Translator.translate("loginTextFieldUsername"));
        loginTextFieldPassword.setPromptText(Translator.translate("loginTextFieldPassword"));
        PK_OnOff.setText(Translator.translate("PK_BoardOff"));
    }

    public void switchToEnglish() {
        Translator.setLanguage("en");
        updateUI();
    }

    public void switchToDutch() {
        Translator.setLanguage("nl");
        updateUI();
    }

    public void pkBordOnOff() {
        if (PK_OnOff.isSelected()) {
            PK_OnOff.setText(Translator.translate("PK_BoardOn"));
        } else {
            PK_OnOff.setText(Translator.translate("PK_BoardOff"));
        }
    }

    public void displayInfo() {
        String departure = comboBoxDeparture.getValue();
        String arrival = comboBoxArrival.getValue();
        LocalTime departureTime = LocalTime.of(spinnerHour.getValue(), spinnerMinute.getValue());
        List<Route> timetable = buttonBus.isSelected() ? RoutePlanner.BUS_TIMETABLE : RoutePlanner.TRAIN_TIMETABLE;

        // TODO feedback optional -> custom exceptions for routeplanner
        RoutePlanner.planJourney(timetable, departure, arrival, departureTime).ifPresent(journey ->  {
            if (buttonTrain.isSelected()) {
                departurePlatformLabel.setText("Spoor " + journey.getDeparturePlatform());
                arrivalPlatformLabel.setText("Spoor " + journey.getArrivalPlatform());
            } else {
                departurePlatformLabel.setText("");
                arrivalPlatformLabel.setText("");
            }
            departureStationLabel.setText(departure);
            arrivalStationLabel.setText(arrival);
            arrivalTimeLabel.setText(journey.getArrival().toString());
            departureTimeLabel.setText(journey.getDeparture().toString());
            stationsTable.setItems(FXCollections.observableArrayList(journey.getNodes()));
            travelTimeTextLabel.setText(journey.getTravelTime() + " minuten");
            journeyInfoPane.setVisible(true);

            StationPair stationPair = new StationPair(departure, arrival);
            ObservableList<StationPair> history = UserData.getInstance().getCurrentUser().getHistory();
            if (!history.contains(stationPair)) {
                history.add(0, stationPair);
            }

            favoritesButton();
        });
    }

    @FXML
    public void login() {
        String username = loginTextFieldUsername.getText();
        String password = loginTextFieldPassword.getText();
        try {
            UserData.getInstance().login(username, password);
            loginTextFieldUsername.setText("");
            loginTextFieldPassword.setText("");
        } catch (InvalidCredentialsException e) {
            unregisteredUserMessage.setText(Translator.translate("incorrectPassword"));
        }
    }

    public void populateCombos(ActionEvent actionEvent) {
        if (buttonBus.isSelected()) {
            List<String> stations = RoutePlanner.getStations(RoutePlanner.BUS_TIMETABLE);
            comboBoxDeparture.setItems(FXCollections.observableList(stations));
        } else {
            List<String> stations = RoutePlanner.getStations(RoutePlanner.TRAIN_TIMETABLE);
            comboBoxDeparture.setItems(FXCollections.observableList(stations));
        }
        onComboAction(actionEvent);
    }

    public void onComboAction(ActionEvent actionEvent) {
        String departure = comboBoxDeparture.getValue();
        if (buttonBus.isSelected()) {
            if (departure != null) {
                List<String> destinations = RoutePlanner.getDestinations(RoutePlanner.BUS_TIMETABLE, departure);
                comboBoxArrival.setItems(FXCollections.observableList(destinations));
            } else {
                comboBoxArrival.setItems(FXCollections.observableList(RoutePlanner.BUS_STATIONS));
            }
        } else {
            if (departure != null) {
                List<String> destinations = RoutePlanner.getDestinations(RoutePlanner.TRAIN_TIMETABLE, departure);
                comboBoxArrival.setItems(FXCollections.observableList(destinations));
            } else {
                comboBoxArrival.setItems(FXCollections.observableList(RoutePlanner.TRAIN_STATIONS));
            }
        }
    }

    public boolean isPKBoardEnabled() {
        return PK_OnOff.isSelected();
    }
}