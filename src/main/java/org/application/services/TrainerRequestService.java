package org.application.services;

import org.application.models.custom.RequestRecord;
import org.application.models.requests.TrainerRequest;
import org.application.models.users.AppUser;
import org.application.models.users.Learner;
import org.application.repositories.custom.CustomRepo;
import org.application.repositories.requests.TrainerRequestRepo;
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
public class TrainerRequestService {

    private CustomRepo<RequestRecord,Long> requestRecordRepo;

    private TrainerRequestRepo trainerRequestRepo;

    private AppUserRepo appUserRepo;

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

        TrainerRequest trainerRequest = getTrainerRequest(start, end, trainer, user);

        trainerRequestRepo.save(trainerRequest);
        requestRecordRepo.save(new RequestRecord("TRAIN_REQ", trainerRequest.getRequester().toString(),
                trainerRequest.getTrainer().toString(), LocalDate.now()));
    }

    private TrainerRequest getTrainerRequest(LocalDateTime start, LocalDateTime end, AppUser trainer, AppUser user) {
        TrainerRequest trainerRequest = new TrainerRequest();
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
        List<TrainerRequest> all = trainerRequestRepo.findAll();

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
    public List<TrainerRequest> getAll() {
        return trainerRequestRepo.findAll();
    }

    @Transactional
    public List<TrainerRequest> getRequestsForTrainer(AppUser trainer) {
        return trainerRequestRepo.findByTrainer(trainer);
    }

    @Transactional
    public void approveRequestTrainer(Long requestId) {
        TrainerRequest one = trainerRequestRepo.getOne(requestId);
        one.setApprovedTrainer(true);
    }

    @Transactional
    public void approveRequestSecurity(Long requestId) {
        TrainerRequest one = trainerRequestRepo.getOne(requestId);
        one.setApprovedSecurity(true);
    }

    @Transactional
    public List<TrainerRequest> getUnapprovedRequests() {
        return getAll().stream().filter(trainerRequest -> (!trainerRequest.getApprovedTrainer() | !trainerRequest.getApprovedSecurity())).collect(toList());
    }

    @Transactional
    public List<TrainerRequest> getApprovedRequests() {
        return getAll().stream().filter(trainerRequest -> (trainerRequest.getApprovedTrainer() & trainerRequest.getApprovedSecurity())).collect(toList());
    }

    @Transactional
    public void removeRequest(Long requestId) {
        TrainerRequest matchedRequest = trainerRequestRepo.getOne(requestId);
        matchedRequest.setRequester(null);
        matchedRequest.setTrainer(null);
        trainerRequestRepo.delete(requestId);
    }
}
