package org.application.repositories.users;

import org.application.models.users.AppUser;
import org.application.models.users.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepo extends JpaRepository<Trainer,Long> {
}
