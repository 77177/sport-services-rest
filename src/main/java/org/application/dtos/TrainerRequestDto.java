package org.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainerRequestDto {

    private Long id;
    private AppUserDto requester;
    private AppUserDto trainer;
    private Timestamp startTime;
    private Timestamp endTime;
    private Boolean approvedTrainer;
    private Boolean approvedSecurity;
}
