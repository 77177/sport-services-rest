package org.application.resources;

import lombok.RequiredArgsConstructor;
import org.application.dtos.TrainerRequestCreateDto;
import org.application.dtos.TrainerRequestDto;
import org.application.models.requests.RoomREquest;
import org.application.services.TrainerRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/v1/api/request/train")
@RequiredArgsConstructor
public class TrainerRequestController {

    private final TrainerRequestService trainerRequestService;
    private final ModelMapper modelMapper;

    @GetMapping("/{requestId}")
    public TrainerRequestDto getTrainerRequest(@PathVariable("requestId") Long trainingRequestId) {
        RoomREquest trainerRequest = trainerRequestService.getTrainerRequest(trainingRequestId);
        return modelMapper.map(trainerRequest, TrainerRequestDto.class);
    }

    @PostMapping("/")
    public TrainerRequestDto createTrainerRequest(@RequestBody TrainerRequestCreateDto trainerRequestCreateDto)
            throws SQLException {
        RoomREquest trainerRequest = trainerRequestService.createTrainerRequest(trainerRequestCreateDto);
        return modelMapper.map(trainerRequest, TrainerRequestDto.class);
    }
    
    @DeleteMapping("/{requestId}")
    public TrainerRequestDto deleteTrainerRequest(@PathVariable("requestId") Long trainingRequestId){
        RoomREquest trainerRequest = trainerRequestService.deleteTrainerRequest(trainingRequestId);
        return modelMapper.map(trainerRequest, TrainerRequestDto.class);
    }
}
