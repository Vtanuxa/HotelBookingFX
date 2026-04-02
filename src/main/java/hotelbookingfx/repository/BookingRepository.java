package hotelbookingfx.repository;

import hotelbookingfx.model.Booking;
import hotelbookingfx.model.Room;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static hotelbookingfx.connection.DBConnection.connection;

public class BookingRepository {
    private List<Booking> bookings = new ArrayList<>();

    public Booking save(Booking booking){
        bookings.add(booking);
        return booking;
    }

    public Optional<Booking> findById(int id){
        return bookings.stream().filter(bookings->bookings.getId()==id).findFirst();
    }
    public List<Booking> findAll(){
        return new ArrayList<>(bookings);
    }

    public List<Booking> findClientId(int clientId){
        return bookings.stream().filter(booking -> booking.getClient().getId()==clientId).collect(Collectors.toList());
    }

    public List<Booking> findActiveBooking(int id){
        return bookings.stream().filter(booking -> booking.getStatus().equals("Активно")).collect(Collectors.toList());
    }

    public List<Booking> findBookingForDate(LocalDate date){
        return bookings.stream()
                .filter(booking -> booking.getStatus().equals("Активно"))
                .filter(booking -> !date.isBefore(booking.getCheckInDate()) && !date.isAfter(booking.getCheckOutDate()))
                .collect(Collectors.toList());
    }

    //Список доступных бронирований на конкретные даты
    public List<Booking> isRoomAvailable(Room room, LocalDate checkIn, LocalDate checkOut){
        return bookings.stream()

                .filter(booking -> booking.getStatus().equals("Активно"))
                .filter(booking -> checkIn.isBefore(booking.getCheckOutDate()) && checkOut.isAfter(booking.getCheckInDate()))
                .collect(Collectors.toList());
    }

    public boolean cancelBooking(int id){
        Optional<Booking> bookingOpt = findById(id);
        if(bookingOpt.isPresent()){
            Booking booking = bookingOpt.get();
            booking.cancel();
            return true;
        }
        return false;
    }

    public boolean delete(int id){
        return bookings.removeIf(booking -> booking.getId()==id);
    }

    public void clean(){
        bookings.clear();
    }
//
//    public Booking save(Booking booking) {
//
//        String saveBooking = "INSERT INTO bookings VALUES (client, room, checkInDate, checkOutDate, guestCount, totalPrice, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
//        try {
//            Connection connection = connection();
//            PreparedStatement preparedStatement = connection.prepareStatement(saveBooking);
//            preparedStatement.setInt(1, booking.getClient().getId));
//            preparedStatement.setInt(2, booking.getRoom());
//            preparedStatement.setDate(3, booking.getCheckInDate());
//            preparedStatement.setDate();
//
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return booking;
//    }
//
//    public Optional<Booking> findById(int id) {
//        return bookings.stream()
//                .filter(booking -> booking.getId() == id)
//                .findFirst();
//    }
//
//    public Optional<Booking> findByClientId(int Id) {
//        return bookings.stream()
//                .filter(booking -> booking.getId() == Id)
//                .findFirst();
//    }
//
//    public List<Booking> findAll() {
//        return new ArrayList<>(bookings);
//    }
//
//    public boolean update(Booking updatedBooking) {
//        Optional<Booking> existing = findById(updatedBooking.getId());
//        if (existing.isPresent()) {
//            Booking booking = existing.get();
//            booking.setCheckInDate(updatedBooking.getCheckInDate());
//            booking.setCheckOutDate(updatedBooking.getCheckOutDate());
//            booking.setStatus(updatedBooking.getStatus());
//            return true;
//        }
//        return false;
//    }
//
//    public boolean delete(int id) {
//        return bookings.removeIf(booking -> booking.getId() == id);
//    }
//
//    public void clear() {
//        bookings.clear();
//    }
}