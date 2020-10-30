package org.application.resources;

import org.application.dtos.RoomDto;
import org.application.dtos.RoomRequestDto;
import org.application.models.Room;
import org.application.services.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/room")
public class RoomController {

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

    @GetMapping("/{roomId}")
    public RoomDto getRoom(@PathVariable("roomId") Long roomId){
        Room room = roomService.getRoom(roomId);
        return modelMapper.map(room, RoomDto.class);
    }

    @DeleteMapping("/{roomId}")
    public RoomDto deleteRoom(@PathVariable("roomId") Long roomId){
        Room room = roomService.deleteRoom(roomId);
        return modelMapper.map(room, RoomDto.class);
    }

    @PostMapping("/")
    public RoomDto createRoom(@RequestBody RoomDto roomDto){
        Room room = roomService.createRoom(modelMapper.map(roomDto, Room.class));
        return modelMapper.map(room, RoomDto.class);
    }
}
