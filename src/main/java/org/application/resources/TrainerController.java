package org.application.resources;

import org.application.dtos.TrainerDto;
import org.application.services.AppUserService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/api/trainer")
public class TrainerController extends CrossOriginController {

    private final AppUserService appUserService;
    private final ModelMapper modelMapper;

    public TrainerController(AppUserService appUserService, ModelMapper modelMapper) {
        this.appUserService = appUserService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public List<TrainerDto> getTrainers(){
        return appUserService.getTrainers()
                .stream()
                .map(appUser -> modelMapper.map(appUser, TrainerDto.class))
                .collect(Collectors.toList());
    }
}
