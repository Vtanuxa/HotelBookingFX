package hotelbookingfx.model;

import java.util.Objects;

public class Room {
    private int id;
    private int roomNumber;
    private String type; // Одноместный, Двухместный, Люкс, Полулюкс
    private String status; // Свободен, Занят, На уборке
    private double pricePerNight; // Стоимость

    public Room(int roomNumber, String type, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.pricePerNight = pricePerNight;
    }

    public int getId() {
        return id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }


    @Override
    public String toString() {
        return String.format(
                "ID: %-3d | Номер: %-4d | Тип: %-12s | Статус: %-8s | Цена: %8.2f руб \n", id, roomNumber, type, status, pricePerNight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

