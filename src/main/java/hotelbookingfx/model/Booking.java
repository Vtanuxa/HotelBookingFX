package hotelbookingfx.model;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Booking{
    private static int nextId = 1;

    private int id;
    private Client client;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int guestsCount;
    private double totalPrice;
    private String status; // Активно, Отменено, Завершено

    public Booking(Client client, Room room, LocalDate checkInDate, LocalDate checkOutDate, int guestsCount) {
        this.id = nextId++;
        this.client = client;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.guestsCount = guestsCount;
        this.totalPrice = calculateTotalPrice();
        this.status = "Активно";
    }

    private double calculateTotalPrice(){
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        return nights * room.getPricePerNight();
    }

    public int getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getGuestsCount() {
        return guestsCount;
    }

    public void setGuestsCount(int guestsCount) {
        this.guestsCount = guestsCount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void cancel(){
        this.status = "Отменено";
        this.room.setStatus("Свободен");
    }

    @Override
    public String toString(){
        return String.format(
                "Бронь #%d [%s] | Клиент: %s | Номер: %s | Даты: %s - %s | Гостей: %d | Сумма: %.2f руб.",
                id, status, client.getFullName(), room.getRoomNumber(), checkInDate, checkOutDate, guestsCount, totalPrice
        );
    }

}
