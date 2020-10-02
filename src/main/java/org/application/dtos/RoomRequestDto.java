package org.application.dtos;

import org.application.models.Room;
import org.application.models.users.AppUser;

import java.sql.Timestamp;

public class RoomRequestDto {
    private Long id;
    private AppUserDto requester;
    private Room room;
    private Timestamp startTime;
    private Timestamp endTime;
    private Boolean approvedAdmin;
    private Boolean approvedSecurity;
}
