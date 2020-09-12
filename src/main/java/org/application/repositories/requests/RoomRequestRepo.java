package org.application.repositories.requests;

import org.application.models.Room;

import org.application.models.requests.RoomRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRequestRepo extends JpaRepository<RoomRequest,Long> {
}
