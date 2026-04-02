package hotelbookingfx.service;

import hotelbookingfx.model.Room;
import hotelbookingfx.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

public class RoomService {
    private RoomRepository roomRepository;

    public RoomService() {
        this.roomRepository = new RoomRepository();
    }

    public Room addRoom(String roomNumber, String type, double pricePerNight){
        if (roomRepository.findByRoomNumber(roomNumber).isPresent()){
            System.out.println("Ошибка: Комната с таким номер уже существует!");
            return null;
        }
        Room room = new Room(roomNumber, type, pricePerNight);
        return roomRepository.save(room);
    }

    public Optional<Room> findRoomById(int id){
        return roomRepository.findById(id);
    }

    public Optional<Room> findRoomByNumber(String roomNumber){
        return roomRepository.findByRoomNumber(roomNumber);
    }


    public List<Room> getAllRooms(){
        return roomRepository.findAll();
    }

    public List<Room> getAvailableRooms(){
        return roomRepository.findByStatus("Свободен");
    }


    public boolean updateRoom(int id, String roomNumber, String type, double pricePerNight){
        Optional<Room> roomOpt = roomRepository.findById(id);
        if (roomOpt.isPresent()){
            Room room = roomOpt.get();
            if (!room.getRoomNumber().equals(roomNumber)){
                if(roomRepository.findByRoomNumber(roomNumber).isPresent()){
                    System.out.println("Ошибка: Комната с таким номером уже существует!");
                    return false;
                }
            }
            room.setRoomNumber(roomNumber);
            room.setType(type);
            room.setPricePerNight(pricePerNight);
            return true;
        }
        System.out.println("Ошибка: Комната не найдена!");
        return false;
    }

    public boolean changeRoomStatus(int id, String newStatus){
        Optional<Room> roomOpt = roomRepository.findById(id);
        if (roomOpt.isPresent()){
            roomOpt.get().setStatus(newStatus);
            return true;
        }
        return false;
    }

    public List<Room> searchRooms(String type, Double minPrice, Double maxPrice, String status){
        List<Room> results = roomRepository.findAll();
        if(type != null && !type.isEmpty()) {
            results.retainAll(roomRepository.findByType(type)); // Пересечение множеств
        }
        if (maxPrice != null || minPrice != null){
            results.retainAll(roomRepository.findByPriceRange(minPrice, maxPrice));
        }
        if (status != null && !status.isEmpty()){
            results.retainAll(roomRepository.findByStatus(status));
        }
        return results;
    }

    public boolean deleteRoom(int id){
        Optional<Room> room = roomRepository.findById(id);
        if (room.isPresent()){
            if (!room.get().getStatus().equals("Свободен")){
                System.out.println("Нельзя удалить занятый номер!");
                return false;
            }
            return roomRepository.delete(id);
        }
        return false;
    }

}
