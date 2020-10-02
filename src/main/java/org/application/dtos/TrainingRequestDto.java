package org.application.dtos;

import java.sql.Timestamp;

public class TrainingRequestDto {

    private Long id;
    private AppUserDto requester;
    private AppUserDto trainer;
    private Timestamp startTime;
    private Timestamp endTime;
    private Boolean approvedTrainer;
    private Boolean approvedSecurity;
}
