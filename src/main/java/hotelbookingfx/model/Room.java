package hotelbookingfx.model;

import java.util.Objects;

public class Room {
    private static int nextId = 1;
    private int id;
    private String roomNumber;
    private String type; // Одноместный, Двухместный, Люкс, Полулюкс
    private String status; // Свободен, Занят, На уборке
    private double pricePerNight; // Стоимость

    public Room(String roomNumber, String type, double pricePerNight) {
        this.id = nextId++;
        this.roomNumber = roomNumber;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.status = "Свободен";
    }

    public int getId() {
        return id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id;
    }
}