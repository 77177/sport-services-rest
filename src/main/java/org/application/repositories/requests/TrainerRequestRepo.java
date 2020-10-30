package org.application.repositories.requests;

import org.application.models.requests.RoomREquest;
import org.application.models.users.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainerRequestRepo extends JpaRepository<RoomREquest,Long> {
    List<RoomREquest> findByTrainer(AppUser trainer);
}
