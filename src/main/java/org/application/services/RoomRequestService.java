package org.application.services;

import org.application.models.Room;
import org.application.models.custom.RequestRecord;
import org.application.models.requests.RoomRequest;
import org.application.models.users.AppUser;
import org.application.models.users.Trainer;
import org.application.repositories.RoomRepo;
import org.application.repositories.custom.CustomRepo;
import org.application.repositories.requests.RoomRequestRepo;
import org.application.repositories.users.AppUserRepo;
import org.springframework.data.util.Pair;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class RoomRequestService {

    private CustomRepo requestRecordRepo;

    private RoomRequestRepo roomRequestRepo;

    private AppUserRepo appUserRepo;

    private RoomRepo roomRepo;

    public RoomRequestService(RoomRequestRepo roomRequestRepo, AppUserRepo appUserRepo, RoomRepo roomRepo, CustomRepo requestRecordRepo) {
        this.roomRequestRepo = roomRequestRepo;
        this.appUserRepo = appUserRepo;
        this.roomRepo = roomRepo;
        this.requestRecordRepo = requestRecordRepo;
    }

    @Transactional
    public void addRoomRequest(Long roomId, LocalDateTime start, LocalDateTime end) throws SQLException {

        checkForOverlap(start, end, roomId);

        User auth = getPrincipal();
        Room room = roomRepo.getOne(roomId);
        AppUser user = appUserRepo.findByUsername(auth.getUsername());

        RoomRequest roomRequest = getRoomRequest(start, end, room, user);

        roomRequestRepo.save(roomRequest);
        requestRecordRepo.save(new RequestRecord("ROOM_REQ", roomRequest.getRequester().toString(),
                roomRequest.getRoom().toString(), LocalDate.now()));
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

    private User getPrincipal() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }



    @Transactional
    public List<RoomRequest> getAll() {
        return roomRequestRepo.findAll();
    }

    @Transactional
    public List<RoomRequest> getUnapprovedRequests() {
        return getAll().stream().filter(roomRequest -> (!roomRequest.getApprovedAdmin() | !roomRequest.getApprovedSecurity())).collect(toList());
    }

    @Transactional
    public List<RoomRequest> getApprovedRequests() {
        return getAll().stream().filter(roomRequest -> (roomRequest.getApprovedAdmin() & roomRequest.getApprovedSecurity())).collect(toList());
    }

    @Transactional
    public void approveRequestAdmin(Long requestId) {
        RoomRequest one = roomRequestRepo.getOne(requestId);
        one.setApprovedAdmin(true);
    }

    @Transactional
    public void approveRequestSecurity(Long requestId) {
        RoomRequest one = roomRequestRepo.getOne(requestId);
        one.setApprovedSecurity(true);
    }

    @Transactional
    public void removeRequest(Long requestId) {
        RoomRequest matchedRoomRequest = roomRequestRepo.getOne(requestId);
        matchedRoomRequest.setRoom(null);
        matchedRoomRequest.setRequester(null);
        roomRequestRepo.delete(requestId);
    }
}
