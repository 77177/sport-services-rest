package org.application.services;

import org.application.models.Room;
import org.application.repositories.RoomRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepo roomRepo;

    public RoomService(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    @Transactional
    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }

    @Transactional
    public Room createRoom(Room room) {
        return roomRepo.save(room);
    }

    @Transactional
    public Room getRoom(Long roomId) {
        return roomRepo.findOne(roomId);
    }

    @Transactional
    public Room deleteRoom(Long roomId) {
        Room one = roomRepo.findOne(roomId);
        roomRepo.delete(roomId);
        return one;
    }
}
