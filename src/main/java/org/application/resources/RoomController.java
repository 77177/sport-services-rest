package org.application.resources;

import org.application.dtos.RoomRequestDto;
import org.application.models.requests.RoomRequest;
import org.application.models.users.AppUser;
import org.application.services.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/room")
public class RoomController {

    private final RoomService roomService;
    private final ModelMapper modelMapper;

    public RoomController(RoomService roomService, ModelMapper modelMapper) {
        this.roomService = roomService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/test")
    public void test(){
        RoomRequest roomRequest = new RoomRequest();
        AppUser appUser = new AppUser();
        appUser.setEnabled(true);
        roomRequest.setRequester(appUser);
        RoomRequestDto map = modelMapper.map(roomRequest, RoomRequestDto.class);
        System.out.println(map);
    }
}
