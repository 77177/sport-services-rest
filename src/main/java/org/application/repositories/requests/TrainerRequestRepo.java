package org.application.repositories.requests;

import org.application.models.requests.TrainerRequest;
import org.application.models.users.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainerRequestRepo extends JpaRepository<TrainerRequest,Long> {
    List<TrainerRequest> findByTrainer(AppUser trainer);
    List<TrainerRequest> findTrainerRequestByRequester(AppUser requester);
}
