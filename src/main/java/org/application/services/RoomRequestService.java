package org.application.services;

import org.application.dtos.RoomRequestCreateDto;
import org.application.models.Room;
import org.application.models.requests.RoomRequest;
import org.application.models.users.AppUser;
import org.application.models.users.Trainer;
import org.application.repositories.RoomRepo;
import org.application.repositories.requests.RoomRequestRepo;
import org.application.repositories.users.AppUserRepo;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


@Service
public class RoomRequestService {

    private final RoomRequestRepo roomRequestRepo;

    private final AppUserRepo appUserRepo;

    private final RoomRepo roomRepo;

    public RoomRequestService(RoomRequestRepo roomRequestRepo, AppUserRepo appUserRepo, RoomRepo roomRepo) {
        this.roomRequestRepo = roomRequestRepo;
        this.appUserRepo = appUserRepo;
        this.roomRepo = roomRepo;
    }

    private void checkForOverlap(LocalDateTime start, LocalDateTime end, Long roomId) {
        List<RoomRequest> all = roomRequestRepo.findAll();

        Boolean isOverlapping = all.stream()
                .filter(roomRequest -> roomRequest.getRoom().getId().equals(roomId))
                .map(roomRequest -> Pair.of(roomRequest.getStartTime(), roomRequest.getEndTime()))
                .map(pair -> areOverlapping(start,end,pair.getFirst().toLocalDateTime(),pair.getSecond().toLocalDateTime()))
                .reduce(Boolean.FALSE, (init, next) -> init || next);

        if (isOverlapping){
            throw new IllegalArgumentException("Overlapping time");
        } else if (start.isAfter(end)) {
            throw new IllegalArgumentException("end is before start");
        }
    }

    private boolean areOverlapping(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2) {
        boolean oneIsBeforeTwo = start1.isBefore(start2) && end1.isBefore(start2);
        boolean twoIsBeforeOne = start2.isBefore(start1) && end2.isBefore(start1);

        return !(oneIsBeforeTwo || twoIsBeforeOne);
    }

    private RoomRequest getRoomRequest(LocalDateTime start, LocalDateTime end, Room room, AppUser user) {
        RoomRequest roomRequest = new RoomRequest();
        roomRequest.setRequester(user);
        roomRequest.setRoom(room);
        roomRequest.setStartTime(Timestamp.valueOf(start));
        roomRequest.setEndTime(Timestamp.valueOf(end));
        ((Trainer) user).getRoomRequests().add(roomRequest);
        return roomRequest;
    }

    @Transactional
    public List<RoomRequest> getAll() {
        return roomRequestRepo.findAll();
    }

    @Transactional
    public void approveRequestAdmin(Long requestId) {
        RoomRequest one = roomRequestRepo.findOne(requestId);
        one.setApprovedAdmin(true);
    }

    @Transactional
    public void approveRequestSecurity(Long requestId) {
        RoomRequest one = roomRequestRepo.findOne(requestId);
        one.setApprovedSecurity(true);
    }

    @Transactional
    public RoomRequest getRoomRequest(Long roomRequestId) {
        return roomRequestRepo.findOne(roomRequestId);
    }

    @Transactional
    public RoomRequest createRoomRequest(RoomRequestCreateDto roomRequestCreateDto) {
        LocalDateTime start = LocalDateTime.ofInstant(roomRequestCreateDto.getStart().toInstant(), ZoneId.systemDefault());
        LocalDateTime end = LocalDateTime.ofInstant(roomRequestCreateDto.getEnd().toInstant(), ZoneId.systemDefault());
        Long roomId = roomRequestCreateDto.getRoomId();
        checkForOverlap(start, end, roomId);
        Room room = roomRepo.findOne(roomId);
        Long trainerId = roomRequestCreateDto.getTrainerId();
        AppUser user = appUserRepo.findOne(trainerId);
        RoomRequest roomRequest = getRoomRequest(start, end, room, user);
        return roomRequestRepo.save(roomRequest);
    }

    @Transactional
    public RoomRequest deleteRoomRequest(Long roomRequestId) {
        RoomRequest matchedRoomRequest = roomRequestRepo.findOne(roomRequestId);
        matchedRoomRequest.setRoom(null);
        matchedRoomRequest.setRequester(null);
        roomRequestRepo.delete(roomRequestId);
        return matchedRoomRequest;
    }

    @Transactional
    public List<RoomRequest> getRoomRequestsForTrainer(Long trainerId) {
        AppUser trainer = appUserRepo.findOne(trainerId);
        return roomRequestRepo.findByRequester(trainer);
    }
}
