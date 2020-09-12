package org.application.services;


import org.application.models.users.AppUser;
import org.application.models.users.Trainer;
import org.application.repositories.users.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppUserServiceTest {

    @Mock
    private AppUserRepo appUserRepo;

    @Mock
    private TrainerRepo trainerRepo;

    @Mock
    private AdminRepo adminRepo;

    @Mock
    private SecurityRepo securityRepo;

    @Mock
    private LearnerRepo learnerRepo;

    @Spy
    @InjectMocks
    private AppUserService appUserService;

    @Test
    public void getTrainers() {
        AppUser trainer = new AppUser();
        AppUser learner = new AppUser();

        trainer.setAuthority("ROLE_TRAINER");
        learner.setAuthority("ROLE_USER");

        List<AppUser> appUsers = new ArrayList<>();
        appUsers.add(trainer);
        appUsers.add(learner);

        when(appUserRepo.findAll()).thenReturn(appUsers);

        List<AppUser> trainers = appUserService.getTrainers();

        assertEquals(1, trainers.size());
        assertEquals("ROLE_TRAINER", trainers.get(0).getAuthority());
    }

    @Test
    public void createUser() {
        AppUser trainer = new AppUser();
        trainer.setPassword("test");
        trainer.setAuthority("ROLE_TRAINER");

        Trainer savedTrainer = new Trainer();
        savedTrainer.setId(0L);
        savedTrainer.setPassword("test");
        savedTrainer.setAuthority("ROLE_TRAINER");

        when(trainerRepo.save(Mockito.any(Trainer.class))).thenReturn(savedTrainer);

        long savedUserId = appUserService.createUser(trainer);

        assertEquals(0, savedUserId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createUserError() {
        AppUser trainer = new AppUser();

        trainer.setPassword("test");
        trainer.setAuthority("ROLE_ERROR");

        appUserService.createUser(trainer);
    }
}