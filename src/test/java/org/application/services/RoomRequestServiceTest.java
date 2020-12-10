package org.application.services;

import org.application.models.requests.RoomRequest;
import org.application.repositories.requests.RoomRequestRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoomRequestServiceTest {

    @Mock
    private RoomRequestRepo roomRequestRepo;

    @InjectMocks
    private RoomRequestService roomRequestService;

    @Test
    public void getAll() {
        roomRequestService.getAll();
        verify(roomRequestRepo).findAll();
    }

    @Test
    public void approveRequestAdmin() {
        Long testId = 100L;
        RoomRequest roomRequest = mock(RoomRequest.class);

        doReturn(roomRequest).when(roomRequestRepo).findOne(testId);
        roomRequestService.approveRequestAdmin(testId);

        verify(roomRequestRepo).findOne(testId);
        verify(roomRequest).setApprovedSecurity(true);
    }
}