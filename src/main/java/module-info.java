module org.test.pwgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;


    opens org.test.pwgui to javafx.fxml;
    exports org.test.pwgui;
}