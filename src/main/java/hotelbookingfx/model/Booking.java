package hotelbookingfx.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Booking {
    private static int nextID = 1;

    private int id;
    private Client client;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int guestCount;
    private double totalPrice;
    private String status; // Активно, Отменено, Завершено

    public Booking(Client client, Room room, LocalDate checkInDate, LocalDate checkOutDate, int guestCount) {
        this.id =  nextID++;
        this.client = client;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.guestCount = guestCount;
        this.totalPrice = totalPrice;
        this.status = "Активно";
    }

    public int getId() {
        return id;
    }


    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public String getStatus() {
        return status;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private double calculateTotalPrice() {
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        return nights * room.getPricePerNight();
    }


}
