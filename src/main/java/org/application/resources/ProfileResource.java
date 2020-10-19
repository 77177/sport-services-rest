package org.application.resources;

import org.application.models.users.AppUser;
import org.application.services.AppUserService;
import org.application.services.RoomRequestService;
import org.application.services.TrainerRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/profile")
public class ProfileResource {

    private AppUserService appUserService;


    private RoomRequestService roomRequestService;


    private TrainerRequestService trainerRequestService;

    public ProfileResource(AppUserService appUserService, RoomRequestService roomRequestService, TrainerRequestService trainerRequestService) {
        this.appUserService = appUserService;
        this.roomRequestService = roomRequestService;
        this.trainerRequestService = trainerRequestService;
    }

    @GetMapping("/rooms/approve")
    public String approveRoomRequestAdmin(@RequestParam("id") Long requestId) {

        roomRequestService.approveRequestAdmin(requestId);

        return "redirect:/profile/primary";
    }

    @GetMapping("/rooms/reject")
    public String rejectRoomRequest(@RequestParam("id") Long requestId) {

        roomRequestService.removeRequest(requestId);

        return "redirect:/profile/primary";
    }

    @GetMapping("/trainers/reject")
    public String rejectTrainerRequest(@RequestParam("id") Long requestId) {

        trainerRequestService.removeRequest(requestId);

        return "redirect:/profile/primary";
    }

    @GetMapping("/trainers/approve")
    public String approveTrainerRequestTrainer(@RequestParam("id") Long requestId) {

        trainerRequestService.approveRequestTrainer(requestId);

        return "redirect:/profile/primary";
    }

    @GetMapping("/rooms/approve/sec")
    public String approveRoomRequestSecurity(@RequestParam("id") Long requestId) {

        roomRequestService.approveRequestSecurity(requestId);

        return "redirect:/profile/primary";
    }

    @GetMapping("/trainers/approve/sec")
    public String approveTrainerRequestSecurity(@RequestParam("id") Long requestId) {

        trainerRequestService.approveRequestSecurity(requestId);

        return "redirect:/profile/primary";
    }


    @GetMapping("/primary")
    public ModelAndView getProfile() {
        ModelAndView modelAndView = new ModelAndView("profile");
        AppUser appUser = appUserService.getCurrentUserInfo();
        modelAndView.addObject("appUser", appUser);
        modelAndView.addObject("unapprovedRoomRequests", roomRequestService.getUnapprovedRequests());
        modelAndView.addObject("unapprovedTrainingRequests", trainerRequestService.getUnapprovedRequests());
        modelAndView.addObject("trainerRequestsForCurrentTrainer", trainerRequestService.getRequestsForTrainer(appUser));
        return modelAndView;
    }
}
