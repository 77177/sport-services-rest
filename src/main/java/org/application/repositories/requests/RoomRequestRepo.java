package org.application.repositories.requests;

import org.application.models.Room;

import org.application.models.requests.RoomRequest;
import org.application.models.users.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRequestRepo extends JpaRepository<RoomRequest,Long> {
    List<RoomRequest> findByRequester(AppUser trainer);
}
