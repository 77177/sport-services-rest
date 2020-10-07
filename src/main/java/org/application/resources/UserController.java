package org.application.resources;


import org.application.dtos.AdminDto;
import org.application.dtos.AppUserDto;
import org.application.services.AppUserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/user")
public class UserController {

    private final AppUserService appUserService;

    public UserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/{id}")
    public AppUserDto getUser(@PathVariable("id") Long id){
        return appUserService.getUser(id);
    }

    @DeleteMapping("/{id}")
    public AppUserDto deleteUser(@PathVariable("id") Long id){
        return appUserService.deleteUser(id);
    }

    @PostMapping("/{id}")
    public long createUser(@PathVariable("id") Long id, @RequestBody AppUserDto appUserDto){
        appUserDto.setId(id);
        return appUserService.createUser(appUserDto);
    }
}
