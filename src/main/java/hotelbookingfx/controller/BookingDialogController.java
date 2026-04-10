package hotelbookingfx.controller;

import hotelbookingfx.model.Booking;
import hotelbookingfx.model.Client;
import hotelbookingfx.model.Room;
import hotelbookingfx.repository.BookingRepository;
import hotelbookingfx.repository.ClientRepository;
import hotelbookingfx.repository.RoomRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.util.List;

public class BookingDialogController {
    private RoomRepository roomRepository;
    private ClientRepository clientRepository;
    private BookingRepository bookingRepository;

    @FXML
    private ComboBox<Client> clientComboBox;
    @FXML
    private ComboBox<Room> roomComboBox;
    @FXML
    private DatePicker checkInDatePicker;
    @FXML
    private DatePicker checkOutDatePicker;
    @FXML
    private Label fio;
    @FXML
    private Label phone;
    @FXML
    private Label passport;
    @FXML
    private Label email;
    @FXML
    private Label type;
    @FXML
    private Label roomNumber;
    @FXML
    private Label pricePerNight;
    @FXML
    private Label status;


    public void setRepositories(RoomRepository roomRepository, ClientRepository clientRepository, BookingRepository bookingRepository){
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
        this.clientRepository = clientRepository;

        loadData();
    }

    private void loadData(){
        List<Client> clients = clientRepository.findAll();
        clientComboBox.getItems().addAll(clients);
        List<Room> rooms = roomRepository.findByStatus("Свободен");
        roomComboBox.getItems().addAll(rooms);
        if (roomComboBox.getItems().isEmpty()){
            System.out.println("Нет свободных комнат"); // Надо перенести это в Label
        }
    }

    public Booking getBooking(){
        Client selectedClient = clientComboBox.getValue();
        Room selectRoom = roomComboBox.getValue();
        LocalDate checkIn = checkInDatePicker.getValue();
        LocalDate checkOut = checkOutDatePicker.getValue();
        int guests = 1; // Надо ещё добавить поле для кол-во гостей
        return new Booking(selectedClient, selectRoom, checkIn, checkOut, guests);
    }
    @FXML
    private void updateClientInfo(){
        Client selected = clientComboBox.getValue();
        if(selected != null){
            fio.setText(selected.getFullName());
            phone.setText(selected.getPhone());
            passport.setText(selected.getPassport());
            email.setText(selected.getEmail());
        }else{
            fio.setText("не выбрано");
            phone.setText("не выбрано");
            passport.setText("не выбрано");
            email.setText("не выбрано");
        }
    }
    @FXML
    private void updateRoomInfo(){
        Room selected = roomComboBox.getValue();
        if (selected != null){
            type.setText(selected.getType());
            roomNumber.setText(selected.getRoomNumber());
            pricePerNight.setText(String.valueOf(selected.getPricePerNight()));
            status.setText(selected.getStatus());
        }
    }

}