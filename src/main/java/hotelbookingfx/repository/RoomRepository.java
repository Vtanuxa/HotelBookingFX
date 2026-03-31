package hotelbookingfx.repository;


import hotelbookingfx.model.Room;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static hotelbookingfx.connection.DBConnection.connection;

public class RoomRepository {

    public Room save(@NotNull Room room) {

        String saveRoom = "INSERT INTO rooms (room_number, type, status, price_per_night) VALUES (?, ?, ?, ?)";

        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveRoom, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, room.getRoomNumber());
            preparedStatement.setString(2, room.getType());
            preparedStatement.setString(3, "Свободен");
            preparedStatement.setDouble(4, room.getPricePerNight());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    room.setId(generatedKeys.getInt(1));
                }
            }
            System.out.println("Новая комната успешно добавлена");
            return room;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional <Room> findById(int id)  {
        String findRoomById = "SELECT * FROM rooms WHERE id = ?";
        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(findRoomById)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Room room = new Room(

                            resultSet.getInt(2),
                            resultSet.getString(3),
                            resultSet.getDouble(5));
                    room.setStatus(resultSet.getString(4));
                    room.setId(resultSet.getInt(1));

                    return Optional.of(room);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public Optional<Room> findByRoomNumber(int roomNumber) {
        String findByRoomNumber = "SELECT * FROM rooms WHERE room_number = ?";

        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(findByRoomNumber)) {

            preparedStatement.setInt(1, roomNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Room room = new Room(

                            resultSet.getInt(2),
                            resultSet.getString(3),
                            resultSet.getDouble(5));
                    room.setId(resultSet.getInt(1));
                    room.setStatus(resultSet.getString(4));
                    return Optional.of(room);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public List <Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        String findAllRooms = "SELECT * FROM rooms";

        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(findAllRooms);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Room room = new Room(
                        resultSet.getInt("room_number"),
                        resultSet.getString("type"),
                        resultSet.getDouble("price_per_night"));
                room.setId(resultSet.getInt("id"));
                room.setStatus(resultSet.getString("status"));
                rooms.add(room);
            }
            return rooms;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean update(Room updateRoom) {
        String updateRoomSql = "UPDATE rooms SET room_number = ?, type = ?, status = ?, price_per_night = ? WHERE id = ?";
        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateRoomSql)) {
            preparedStatement.setInt(1, updateRoom.getRoomNumber());
            preparedStatement.setString(2, updateRoom.getType());
            preparedStatement.setString(3, updateRoom.getStatus());
            preparedStatement.setDouble(4, updateRoom.getPricePerNight());
            preparedStatement.setInt(5, updateRoom.getId());
            return preparedStatement.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(int id) {
        String deleteRoom = "DELETE FROM rooms WHERE id = ?";
        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteRoom)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() {
        String clearRooms = "DELETE FROM rooms";
        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(clearRooms)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
