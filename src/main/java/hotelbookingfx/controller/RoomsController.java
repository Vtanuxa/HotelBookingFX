package hotelbookingfx.controller;

import hotelbookingfx.model.Room;
import hotelbookingfx.repository.RoomRepository;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class RoomsController implements Initializable {
    @FXML
    private TableView<Room> roomsTable;
    @FXML
    private TextField searchField;

    private RoomRepository roomRepository;
    private ObservableList<Room> roomList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roomRepository = RoomRepository.getInstance();
        roomList = FXCollections.observableArrayList();

        setupTableColumns();
        loadRoomsData();
    }

    private void setupTableColumns() {
        TableColumn<Room, Integer> idColumn = (TableColumn<Room, Integer>) roomsTable.getColumns().get(0);
        TableColumn<Room, String> numberColumn = (TableColumn<Room, String>) roomsTable.getColumns().get(1);
        TableColumn<Room, String> typeColumn = (TableColumn<Room, String>) roomsTable.getColumns().get(2);
        TableColumn<Room, Double> priceColumn = (TableColumn<Room, Double>) roomsTable.getColumns().get(3);
        TableColumn<Room, String> statusColumn = (TableColumn<Room, String>) roomsTable.getColumns().get(4);

        idColumn.setCellValueFactory(cellData -> {
            Room room = cellData.getValue();
            return room == null ? new SimpleIntegerProperty(0).asObject()
                    : new SimpleIntegerProperty(room.getId()).asObject();
        });

        numberColumn.setCellValueFactory(cellData -> {
            Room room = cellData.getValue();
            return room == null ? new SimpleStringProperty("")
                    : new SimpleStringProperty(room.getRoomNumber());
        });

        typeColumn.setCellValueFactory(cellData -> {
            Room room = cellData.getValue();
            return room == null ? new SimpleStringProperty("")
                    : new SimpleStringProperty(room.getType());
        });

        priceColumn.setCellValueFactory(cellData -> {
            Room room = cellData.getValue();
            return room == null ? new SimpleDoubleProperty(0).asObject()
                    : new SimpleDoubleProperty(room.getPricePerNight()).asObject();
        });

        statusColumn.setCellValueFactory(cellData -> {
            Room room = cellData.getValue();
            return room == null ? new SimpleStringProperty("")
                    : new SimpleStringProperty(room.getStatus());
        });
    }

    private void loadRoomsData(){
        List<Room> rooms = roomRepository.findAll();
        roomList.clear();
        roomList.addAll(rooms);
        roomsTable.setItems(roomList);
    }

    @FXML
    private void handleAddRoom(){
        showRoomDialog(null);
    }

    private void showRoomDialog(Room room){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotelbookingfx/room-dialog.fxml"));
            DialogPane dialogPane = loader.load();
            RoomDialogController dialogController = loader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle(room ==  null ? "Добавление комнаты" : "Редактирование комнаты");

            Optional<ButtonType> result = dialog.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                Room newRoom = dialogController.getRoomData();
                if (newRoom == null){
                    System.out.println("Комната не сохранена");
                }else{
                    roomRepository.save(newRoom);
                    loadRoomsData();
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
