package org.application.resources;

import lombok.*;
import org.application.dtos.AppUserDto;
import org.application.services.AppUserService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final AppUserService appUserService;
    private final ModelMapper modelMapper;

    @GetMapping()
    public AppUserDto getUsername(){
        return modelMapper.map(appUserService.getCurrentUserInfo(), AppUserDto.class);
    }
}
