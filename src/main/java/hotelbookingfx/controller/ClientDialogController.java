package hotelbookingfx.controller;

import hotelbookingfx.model.Client;
import hotelbookingfx.model.Room;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ClientDialogController {
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passportField;

    private Room currentRoom;

    public Client getClientData(){
        String fullName = fullNameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String passport = passportField.getText().trim();
        if (fullName.isEmpty()){
            System.out.println("Ошибка валидации");
            return null;
        }
        if (phone.isEmpty()){
            System.out.println("Ошибка валидации цены");
            return null;
        }
        if (email.isEmpty()){
            System.out.println("Ошибка валидации цены");
            return null;
        }
        if (passport.isEmpty()){
            System.out.println("Ошибка валидации цены");
            return null;
        }

        return new Client(fullName, phone, email, passport);
    }

}
