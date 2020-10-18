package org.application.resources;

import org.application.models.Room;
import org.application.services.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/api/room")
public class RoomController extends CrossOriginController {

    private final RoomService roomService;
    private final ModelMapper modelMapper;

    public RoomController(RoomService roomService, ModelMapper modelMapper) {
        this.roomService = roomService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public List<Room> getRooms(){
        return roomService.getAllRooms();
    }
}
