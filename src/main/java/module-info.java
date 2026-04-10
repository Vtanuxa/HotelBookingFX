module hotelbookingfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jdi;


    opens hotelbookingfx to javafx.fxml;
    opens hotelbookingfx.controller to javafx.fxml;

    exports hotelbookingfx;
}