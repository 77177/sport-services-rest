package org.application.services;

import org.application.models.users.AppUser;
import org.application.models.users.Trainer;
import org.application.repositories.users.AppUserRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AppUserServiceTest {

    @Mock
    private AppUserRepo appUserRepo;

    @InjectMocks
    private AppUserService appUserService;

    @Test
    public void getTrainers() {
        Trainer trainer = mock(Trainer.class);
        List<Trainer> trainers = Collections.singletonList(trainer);
        doReturn("ROLE_TRAINER").when(trainer).getAuthority();
        doReturn(trainers).when(appUserRepo).findAll();

        List<AppUser> trainersResult = appUserService.getTrainers();

        Assert.assertEquals(trainersResult, trainers);
        verify(appUserRepo).findAll();
    }
}