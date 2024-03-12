module com.example.ov_app {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;

    opens com.example.ov_app to javafx.fxml;

    exports com.example.ov_app;
    exports com.example.ov_app.data;
}