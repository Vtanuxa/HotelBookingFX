package hotelbookingfx.controller;

import hotelbookingfx.model.Room;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class RoomDialogController {
    @FXML
    private TextField roomNumberField;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private TextField priceField;

    private Room currentRoom;

    public Room getRoomData(){
        String roomNumber = roomNumberField.getText().trim();
        String priceText = priceField.getText().trim();
        if (roomNumber.isEmpty()){
            System.out.println("Ошибка валидации номера комнаты");
            return null;
        }
        if (priceText.isEmpty()){
            System.out.println("Ошибка валидации цены");
            return null;
        }
        double price = 0;
        try {
            price = Double.parseDouble(priceText);
            if (price <= 0){
                System.out.println("Ошибка валидации цены");
            }
        }catch (NumberFormatException e){
            System.out.println("Ошибка валидации цены");
        }
        String type = typeComboBox.getValue();
        return new Room(roomNumber, type, price);
    }

}
