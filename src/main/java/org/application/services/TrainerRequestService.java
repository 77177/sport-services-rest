package org.application.services;

import org.application.dtos.TrainerRequestCreateDto;
import org.application.models.custom.RequestRecord;
import org.application.models.requests.RoomREquest;
import org.application.models.users.AppUser;
import org.application.models.users.Learner;
import org.application.repositories.custom.CustomRepo;
import org.application.repositories.requests.TrainerRequestRepo;
import org.application.repositories.users.AppUserRepo;
import org.modelmapper.ModelMapper;
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
public class TrainerRequestService {

    private CustomRepo<RequestRecord,Long> requestRecordRepo;

    private TrainerRequestRepo trainerRequestRepo;

    private AppUserRepo appUserRepo;

    private ModelMapper modelMapper;

    public TrainerRequestService(TrainerRequestRepo trainerRequestRepo, AppUserRepo appUserRepo,
                                 CustomRepo<RequestRecord,Long> requestRecordRepo) {
        this.trainerRequestRepo = trainerRequestRepo;
        this.appUserRepo = appUserRepo;
        this.requestRecordRepo = requestRecordRepo;
    }

    @Transactional
    public void addTrainerRequest(Long trainerId, LocalDateTime start, LocalDateTime end) throws SQLException {

        checkForOverlap(start,end, trainerId);

        User auth = getPrincipal();
        AppUser trainer = appUserRepo.getOne(trainerId);
        AppUser user = appUserRepo.findByUsername(auth.getUsername());

        RoomREquest trainerRequest = getTrainerRequest(start, end, trainer, user);

        trainerRequestRepo.save(trainerRequest);
        requestRecordRepo.save(new RequestRecord("TRAIN_REQ", trainerRequest.getRequester().toString(),
                trainerRequest.getTrainer().toString(), LocalDate.now()));
    }

    private RoomREquest getTrainerRequest(LocalDateTime start, LocalDateTime end, AppUser trainer, AppUser user) {
        RoomREquest trainerRequest = new RoomREquest();
        trainerRequest.setRequester(user);
        trainerRequest.setTrainer(trainer);
        trainerRequest.setStartTime(Timestamp.valueOf(start));
        trainerRequest.setEndTime(Timestamp.valueOf(end));
        ((Learner) user).getTrainerRequests().add(trainerRequest);
        return trainerRequest;
    }

    private User getPrincipal() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private void checkForOverlap(LocalDateTime start, LocalDateTime end, Long trainerId) {
        List<RoomREquest> all = trainerRequestRepo.findAll();

        Boolean isOverlapping = all.stream()
                .filter(trainerRequest -> trainerRequest.getTrainer().getId().equals(trainerId))
                .map(trainerRequest -> Pair.of(trainerRequest.getStartTime(), trainerRequest.getEndTime()))
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

    @Transactional
    public List<RoomREquest> getAll() {
        return trainerRequestRepo.findAll();
    }

    @Transactional
    public List<RoomREquest> getRequestsForTrainer(AppUser trainer) {
        return trainerRequestRepo.findByTrainer(trainer);
    }

    @Transactional
    public void approveRequestTrainer(Long requestId) {
        RoomREquest one = trainerRequestRepo.getOne(requestId);
        one.setApprovedTrainer(true);
    }

    @Transactional
    public void approveRequestSecurity(Long requestId) {
        RoomREquest one = trainerRequestRepo.getOne(requestId);
        one.setApprovedSecurity(true);
    }

    @Transactional
    public List<RoomREquest> getUnapprovedRequests() {
        return getAll().stream().filter(trainerRequest -> (!trainerRequest.getApprovedTrainer() | !trainerRequest.getApprovedSecurity())).collect(toList());
    }

    @Transactional
    public List<RoomREquest> getApprovedRequests() {
        return getAll().stream().filter(trainerRequest -> (trainerRequest.getApprovedTrainer() & trainerRequest.getApprovedSecurity())).collect(toList());
    }

    @Transactional
    public RoomREquest deleteTrainerRequest(Long trainingRequestId) {
        RoomREquest matchedRequest = trainerRequestRepo.getOne(trainingRequestId);
        matchedRequest.setRequester(null);
        matchedRequest.setTrainer(null);
        trainerRequestRepo.delete(trainingRequestId);
        return matchedRequest;
    }

    @Transactional
    public RoomREquest getTrainerRequest(Long trainingRequestId) {
        return trainerRequestRepo.getOne(trainingRequestId);
    }

    @Transactional
    public RoomREquest createTrainerRequest(TrainerRequestCreateDto trainerRequestCreateDto) throws SQLException {

        LocalDateTime start = trainerRequestCreateDto.getStart();
        LocalDateTime end = trainerRequestCreateDto.getEnd();
        Long trainerId = trainerRequestCreateDto.getTrainerId();
        checkForOverlap(start, end, trainerId);

        AppUser trainer = appUserRepo.getOne(trainerId);
        Long requesterId = trainerRequestCreateDto.getRequesterId();
        AppUser user = appUserRepo.getOne(requesterId);

        RoomREquest trainerRequest = getTrainerRequest(start, end, trainer, user);

        RoomREquest savedRequest = trainerRequestRepo.save(trainerRequest);
        requestRecordRepo.save(new RequestRecord("TRAIN_REQ", trainerRequest.getRequester().toString(),
                trainerRequest.getTrainer().toString(), LocalDate.now()));

        return savedRequest;
    }
}
