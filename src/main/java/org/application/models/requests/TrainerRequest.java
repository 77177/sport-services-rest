package org.application.models.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.application.models.users.AppUser;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TrainerRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AppUser requester;

    @OneToOne
    private AppUser trainer;

    @Column
    private Timestamp startTime;

    @Column
    private Timestamp endTime;

    @Column
    private Boolean approvedTrainer = false;

    @Column
    private Boolean approvedSecurity = false;
}
