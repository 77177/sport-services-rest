package org.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.application.models.Room;
import org.application.models.users.AppUser;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequestDto {
    private Long id;
    private AppUserDto requester;
    private Room room;
    private Timestamp startTime;
    private Timestamp endTime;
    private Boolean approvedAdmin;
    private Boolean approvedSecurity;
}
