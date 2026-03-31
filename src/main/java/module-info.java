module hotelbookingfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.jetbrains.annotations;


    opens hotelbookingfx to javafx.fxml;
    opens hotelbookingfx.controller to javafx.fxml;

    exports hotelbookingfx;
}