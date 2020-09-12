package org.application.repositories;

import org.application.models.Room;
import org.application.models.users.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepo extends JpaRepository<Room, Long> {
}
