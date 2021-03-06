package org.application.resources;

import lombok.RequiredArgsConstructor;
import org.application.dtos.RoomRequestCreateDto;
import org.application.dtos.RoomRequestDto;
import org.application.models.requests.RoomRequest;
import org.application.services.RoomRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/api/request/room")
@RequiredArgsConstructor
public class RoomRequestController {

    private final RoomRequestService roomRequestService;
    private final ModelMapper modelMapper;

    @GetMapping("/{requestId}")
    public RoomRequestDto getRoomRequest(@PathVariable("requestId") Long roomRequestId) {
        RoomRequest roomRequest = roomRequestService.getRoomRequest(roomRequestId);
        return modelMapper.map(roomRequest, RoomRequestDto.class);
    }

    @PostMapping("/")
    public RoomRequestDto createRoomRequest(@RequestBody RoomRequestCreateDto roomRequestCreateDto) throws SQLException {
        RoomRequest roomRequest = roomRequestService.createRoomRequest(roomRequestCreateDto);
        return modelMapper.map(roomRequest, RoomRequestDto.class);
    }

    @DeleteMapping("/{requestId}")
    public RoomRequestDto deleteRoomRequest(@PathVariable("requestId") Long trainingRequestId){
        RoomRequest roomRequest = roomRequestService.deleteRoomRequest(trainingRequestId);
        return modelMapper.map(roomRequest, RoomRequestDto.class);
    }

    @GetMapping("/trainer/{trainerId}")
    public List<RoomRequestDto> getRoomRequestsForTrainer(@PathVariable ("trainerId") Long trainerId) {
        List<RoomRequest> roomRequestsForTrainer = roomRequestService.getRoomRequestsForTrainer(trainerId);
        return roomRequestsForTrainer.stream()
                .map(roomRequest -> modelMapper.map(roomRequest, RoomRequestDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/all")
    public List<RoomRequestDto> getAllRoomRequests() {
        List<RoomRequest> roomRequestsForTrainer = roomRequestService.getAll();
        return roomRequestsForTrainer.stream()
                .map(roomRequest -> modelMapper.map(roomRequest, RoomRequestDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/security/approve/{requestId}")
    public void approveTrainerRequestSecurity(@PathVariable("requestId") Long requestId) {
        roomRequestService.approveRequestSecurity(requestId);
    }

    @GetMapping("/admin/approve/{requestId}")
    public void approveTrainerRequestAdmin(@PathVariable("requestId") Long requestId) {
        roomRequestService.approveRequestAdmin(requestId);
    }
}
