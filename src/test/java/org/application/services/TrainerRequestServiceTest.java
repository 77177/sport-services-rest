package org.application.services;

import org.application.models.requests.TrainerRequest;
import org.application.repositories.requests.TrainerRequestRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrainerRequestServiceTest {

    @Mock
    private TrainerRequestRepo trainerRequestRepo;

    @InjectMocks
    private TrainerRequestService trainerRequestService;

    @Test
    public void deleteTrainerRequest() {
        Long testId = 100L;
        TrainerRequest trainerRequest = mock(TrainerRequest.class);
        doReturn(trainerRequest).when(trainerRequestRepo).findOne(testId);

        TrainerRequest trainerRequestResult = trainerRequestService.deleteTrainerRequest(testId);

        assertEquals(trainerRequestResult, trainerRequest);
        verify(trainerRequestRepo).findOne(testId);
        verify(trainerRequestRepo).delete(testId);
        verify(trainerRequest).setRequester(null);
        verify(trainerRequest).setTrainer(null);
    }
}