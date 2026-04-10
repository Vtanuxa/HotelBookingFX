package hotelbookingfx.controller;

import hotelbookingfx.model.Booking;
import hotelbookingfx.model.Client;
import hotelbookingfx.model.Room;
import hotelbookingfx.repository.BookingRepository;
import hotelbookingfx.repository.ClientRepository;
import hotelbookingfx.repository.RoomRepository;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class BookingController {
    @FXML
    private TableView<Booking> bookingsTable;
    private BookingRepository bookingRepository;
    private RoomRepository roomRepository;
    private ClientRepository clientRepository;
    private ObservableList<Booking> bookingsList;

    public void initialize(){
        bookingRepository = new BookingRepository();
        roomRepository = RoomRepository.getInstance();
        clientRepository = ClientRepository.getInstance();
        bookingsList = FXCollections.observableArrayList();

        setupTableColumns();
        loadBookingData();
    }

    private void setupTableColumns(){
        TableColumn<Booking, Integer> bookingId = (TableColumn<Booking, Integer>) bookingsTable.getColumns().get(0);
        TableColumn<Booking, String> client = (TableColumn<Booking, String>) bookingsTable.getColumns().get(1);
        TableColumn<Booking, String> room = (TableColumn<Booking, String>) bookingsTable.getColumns().get(2);
        TableColumn<Booking, LocalDate> checkInDate = (TableColumn<Booking, LocalDate>) bookingsTable.getColumns().get(3);
        TableColumn<Booking, LocalDate> checkOutDate = (TableColumn<Booking, LocalDate>) bookingsTable.getColumns().get(4);
        TableColumn<Booking, Double> totalPrice = (TableColumn<Booking, Double>) bookingsTable.getColumns().get(5);
        TableColumn<Booking, String> status = (TableColumn<Booking, String>) bookingsTable.getColumns().get(6);

        bookingId.setCellValueFactory(cellData -> {
            Booking booking = cellData.getValue();
            return booking == null ? new SimpleIntegerProperty(0).asObject()
                    : new SimpleIntegerProperty(booking.getId()).asObject();
        });

        client.setCellValueFactory(cellData -> {
            Client c = cellData.getValue().getClient();
            return new SimpleStringProperty(c == null ? "Не указан" : c.getFullName());
        });

        room.setCellValueFactory(cellData -> {
            Room r = cellData.getValue().getRoom();
            return new SimpleStringProperty(r == null ? "Не указан" : r.getRoomNumber());
        });

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        checkInDate.setCellValueFactory(tc -> new TableCell<Booking, LocalDate>(){
            @Override
            protected void updateItem(LocalDate date, boolean empty){
                super.updateItem(date, empty);
                if(empty || date == null){
                    setText(null);
                }else{
                    setText(date.format(dateTimeFormatter));
                }
            }
        }.itemProperty());

        checkOutDate.setCellValueFactory(tc -> new TableCell<Booking, LocalDate>(){
            @Override
            protected void updateItem(LocalDate date, boolean empty){
                super.updateItem(date, empty);
                if(empty || date == null){
                    setText(null);
                }else{
                    setText(date.format(dateTimeFormatter));
                }
            }
        }.itemProperty());

        totalPrice.setCellValueFactory(tc -> new TableCell<Booking, Double>(){
            @Override
            protected void updateItem(Double price, boolean empty){
                super.updateItem(price, empty);
                if(empty || price == null){
                    setText(null);
                }else{
                    setText(String.format("%.2f руб.", price));
                }
            }
        }.itemProperty());
        status.setCellValueFactory(cellData -> {
            Booking booking = cellData.getValue();
            return new SimpleStringProperty(booking == null ? "Не указан" : booking.getStatus());
        });
    }

    private void loadBookingData(){
        List<Booking> bookings = bookingRepository.findAll();
        bookingsList.clear();
        bookingsList.addAll(bookings);
        bookingsTable.setItems(bookingsList);
    }

    @FXML
    private void handleAddBooking(){
        showBookingDialog();
    }

    private void showBookingDialog(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotelbookingfx/booking-dialog.fxml"));
            DialogPane dialogPane = loader.load();
            BookingDialogController dialogController = loader.getController();
            dialogController.setRepositories(roomRepository, clientRepository, bookingRepository);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Новое бронирование");
            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK){
                Booking newBooking = dialogController.getBooking();
                if(newBooking != null){
                    Booking savedBooking = bookingRepository.save(newBooking);
                    if (savedBooking != null){
                        Room room = newBooking.getRoom();
                        room.setStatus("Занят");
                        roomRepository.update(room);
                        loadBookingData();
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }



    }

}