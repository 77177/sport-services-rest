package org.application.services;

import org.application.models.Room;
import org.application.repositories.RoomRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RoomService {

    private RoomRepo roomRepo;

    public RoomService(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    @Transactional
    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }

    @Transactional
    public void createRoom(Room room) {
        roomRepo.save(room);
    }
}
