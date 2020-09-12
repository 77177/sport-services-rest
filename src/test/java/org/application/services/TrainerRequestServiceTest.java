package org.application.services;

import org.application.models.requests.TrainerRequest;
import org.application.models.users.AppUser;
import org.application.repositories.requests.TrainerRequestRepo;
import org.application.repositories.users.AppUserRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrainerRequestServiceTest {

    @Mock
    private TrainerRequestRepo trainerRequestRepo;

    @Mock
    private AppUserRepo appUserRepo;

    @Spy
    @InjectMocks
    private TrainerRequestService trainerRequestService;

    @Test
    public void getAll() {
        trainerRequestService.getAll();

        verify(trainerRequestRepo).findAll();
    }

    @Test
    public void getRequestsForTrainer() {
        AppUser trainer = mock(AppUser.class);

        trainerRequestService.getRequestsForTrainer(trainer);

        verify(trainerRequestRepo).findByTrainer(trainer);
    }

    @Test
    public void approveRequestTrainer() {
        TrainerRequest trainerRequest = mock(TrainerRequest.class);

        long id = 1L;
        when(trainerRequestRepo.getOne(id)).thenReturn(trainerRequest);
        trainerRequestService.approveRequestTrainer(id);

        verify(trainerRequestRepo).getOne(id);
    }

    @Test
    public void approveRequestSecurity() {
        TrainerRequest trainerRequest = mock(TrainerRequest.class);

        long id = 1L;
        when(trainerRequestRepo.getOne(id)).thenReturn(trainerRequest);
        trainerRequestService.approveRequestSecurity(id);

        verify(trainerRequestRepo).getOne(id);
    }

    @Test
    public void getUnapprovedRequests() {
        trainerRequestService.getUnapprovedRequests();

        verify(trainerRequestService).getAll();
    }
}