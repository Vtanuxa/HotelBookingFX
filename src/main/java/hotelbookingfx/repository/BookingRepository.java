package hotelbookingfx.repository;

import hotelbookingfx.model.Booking;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static hotelbookingfx.connection.DBConnection.connection;

public class BookingRepository {
    private List<Booking> bookings = new ArrayList<>();

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

    public Optional<Booking> findById(int id) {
        return bookings.stream()
                .filter(booking -> booking.getId() == id)
                .findFirst();
    }

    public Optional<Booking> findByClientId(int Id) {
        return bookings.stream()
                .filter(booking -> booking.getId() == Id)
                .findFirst();
    }

    public List<Booking> findAll() {
        return new ArrayList<>(bookings);
    }

    public boolean update(Booking updatedBooking) {
        Optional<Booking> existing = findById(updatedBooking.getId());
        if (existing.isPresent()) {
            Booking booking = existing.get();
            booking.setCheckInDate(updatedBooking.getCheckInDate());
            booking.setCheckOutDate(updatedBooking.getCheckOutDate());
            booking.setStatus(updatedBooking.getStatus());
            return true;
        }
        return false;
    }

    public boolean delete(int id) {
        return bookings.removeIf(booking -> booking.getId() == id);
    }

    public void clear() {
        bookings.clear();
    }
}