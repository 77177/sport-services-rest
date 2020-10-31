package org.application.resources;

import lombok.RequiredArgsConstructor;
import org.application.dtos.TrainerRequestCreateDto;
import org.application.dtos.TrainerRequestDto;
import org.application.models.requests.TrainerRequest;
import org.application.services.TrainerRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/api/request/train")
@RequiredArgsConstructor
public class TrainerRequestController {

    private final TrainerRequestService trainerRequestService;
    private final ModelMapper modelMapper;

    @GetMapping("/{requestId}")
    public TrainerRequestDto getTrainerRequest(@PathVariable("requestId") Long trainingRequestId) {
        TrainerRequest trainerRequest = trainerRequestService.getTrainerRequest(trainingRequestId);
        return modelMapper.map(trainerRequest, TrainerRequestDto.class);
    }

    @PostMapping("/")
    public TrainerRequestDto createTrainerRequest(@RequestBody TrainerRequestCreateDto trainerRequestCreateDto)
            throws SQLException {
        TrainerRequest trainerRequest = trainerRequestService.createTrainerRequest(trainerRequestCreateDto);
        return modelMapper.map(trainerRequest, TrainerRequestDto.class);
    }
    
    @DeleteMapping("/{requestId}")
    public TrainerRequestDto deleteTrainerRequest(@PathVariable("requestId") Long trainingRequestId){
        TrainerRequest trainerRequest = trainerRequestService.deleteTrainerRequest(trainingRequestId);
        return modelMapper.map(trainerRequest, TrainerRequestDto.class);
    }

    @GetMapping("/learner/{learnerId}")
    public List<TrainerRequestDto> getTrainerRequestForLearner(@PathVariable("learnerId") Long learnerId){
        List<TrainerRequest> trainerRequestsForLearner = trainerRequestService.getTrainerRequestsForLearner(learnerId);
        return trainerRequestsForLearner.stream()
                .map(trainerRequest -> modelMapper.map(trainerRequest, TrainerRequestDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/trainer/{trainerId}")
    public List<TrainerRequestDto> getTrainerRequestsForTrainer(@PathVariable("trainerId") Long trainerId){
        List<TrainerRequest> trainerRequestsForTrainer = trainerRequestService.getTrainerRequestsForTrainer(trainerId);
        return trainerRequestsForTrainer.stream()
                .map(trainerRequest -> modelMapper.map(trainerRequest, TrainerRequestDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/all")
    public List<TrainerRequestDto> getAllTrainerRequest(){
        List<TrainerRequest> trainerRequestsForLearner = trainerRequestService.getAll();
        return trainerRequestsForLearner.stream()
                .map(trainerRequest -> modelMapper.map(trainerRequest, TrainerRequestDto.class))
                .collect(Collectors.toList());
    }
}
