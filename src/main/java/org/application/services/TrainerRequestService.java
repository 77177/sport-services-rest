package org.application.services;

import ch.qos.logback.core.db.dialect.PostgreSQLDialect;
import org.application.dtos.TrainerRequestCreateDto;
import org.application.models.requests.TrainerRequest;
import org.application.models.users.AppUser;
import org.application.models.users.Learner;
import org.application.repositories.requests.TrainerRequestRepo;
import org.application.repositories.users.AppUserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.Pair;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class TrainerRequestService {

    private final TrainerRequestRepo trainerRequestRepo;

    private final AppUserRepo appUserRepo;

    public TrainerRequestService(TrainerRequestRepo trainerRequestRepo, AppUserRepo appUserRepo) {
        this.trainerRequestRepo = trainerRequestRepo;
        this.appUserRepo = appUserRepo;
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
    public void approveRequestTrainer(Long requestId) {
        TrainerRequest one = trainerRequestRepo.findOne(requestId);
        one.setApprovedTrainer(true);
    }

    @Transactional
    public void approveRequestSecurity(Long requestId) {
        TrainerRequest one = trainerRequestRepo.findOne(requestId);
        one.setApprovedSecurity(true);
    }

    @Transactional
    public TrainerRequest deleteTrainerRequest(Long trainingRequestId) {
        TrainerRequest matchedRequest = trainerRequestRepo.findOne(trainingRequestId);
        matchedRequest.setRequester(null);
        matchedRequest.setTrainer(null);
        trainerRequestRepo.delete(trainingRequestId);
        return matchedRequest;
    }

    @Transactional
    public TrainerRequest getTrainerRequest(Long trainingRequestId) {
        return trainerRequestRepo.findOne(trainingRequestId);
    }

    @Transactional
    public TrainerRequest createTrainerRequest(TrainerRequestCreateDto trainerRequestCreateDto) {

        LocalDateTime start = LocalDateTime.ofInstant(trainerRequestCreateDto.getStart().toInstant(), ZoneId.systemDefault());
        LocalDateTime end = LocalDateTime.ofInstant(trainerRequestCreateDto.getEnd().toInstant(), ZoneId.systemDefault());
        Long trainerId = trainerRequestCreateDto.getTrainerId();
        checkForOverlap(start, end, trainerId);

        AppUser trainer = appUserRepo.findOne(trainerId);
        Long requesterId = trainerRequestCreateDto.getRequesterId();
        AppUser user = appUserRepo.findOne(requesterId);

        TrainerRequest trainerRequest = getTrainerRequest(start, end, trainer, user);

        return trainerRequestRepo.save(trainerRequest);
    }

    @Transactional
    public List<TrainerRequest> getTrainerRequestsForLearner(Long learnerId) {
        AppUser learner = appUserRepo.findOne(learnerId);
        return trainerRequestRepo.findTrainerRequestByRequester(learner);
    }

    @Transactional
    public List<TrainerRequest> getTrainerRequestsForTrainer(Long trainerId) {
        AppUser trainer = appUserRepo.findOne(trainerId);
        return trainerRequestRepo.findByTrainer(trainer);
    }
}
