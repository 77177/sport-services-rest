package org.application.resources;

import org.application.services.RoomRequestService;
import org.application.services.TrainerRequestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/request")
public class RequestController {

    private final RoomRequestService roomRequestService;

    private final TrainerRequestService trainerRequestService;

    public RequestController(RoomRequestService roomRequestService, TrainerRequestService trainerRequestService) {
        this.roomRequestService = roomRequestService;
        this.trainerRequestService = trainerRequestService;
    }
}
