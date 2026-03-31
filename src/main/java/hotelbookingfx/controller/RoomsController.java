package hotelbookingfx.controller;

import hotelbookingfx.model.Room;
import hotelbookingfx.repository.RoomRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RoomsController implements Initializable {
    @FXML
    private TableView<Room> roomsTable;
    @FXML
    private TextField searchField;

    private RoomRepository roomRepository;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void setupTableColumns(){
        TableColumn<Room, Integer> idColumn = (TableColumn<Room, Integer>) roomsTable.getColumns().get(0);
        TableColumn<Room, String> numberColumn = (TableColumn<Room, String>) roomsTable.getColumns().get(1);
    }
}
