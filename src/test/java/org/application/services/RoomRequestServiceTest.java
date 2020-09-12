package org.application.services;


import org.application.models.requests.RoomRequest;
import org.application.repositories.requests.RoomRequestRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoomRequestServiceTest {

    @Mock
    private RoomRequestRepo roomRequestRepo;

    @Spy
    @InjectMocks
    private RoomRequestService roomRequestService;

    @Test
    public void getAll() {
        roomRequestService.getAll();

        verify(roomRequestRepo).findAll();
    }

    @Test
    public void getUnapprovedRequests() {
        roomRequestService.getUnapprovedRequests();

        verify(roomRequestService).getAll();
    }

    @Test
    public void approveRequestAdmin() {
        RoomRequest roomRequest = mock(RoomRequest.class);

        long id = 1L;
        when(roomRequestRepo.getOne(id)).thenReturn(roomRequest);
        roomRequestService.approveRequestAdmin(id);

        verify(roomRequestRepo).getOne(id);
    }

    @Test
    public void approveRequestSecurity() {
        RoomRequest roomRequest = mock(RoomRequest.class);

        long id = 1L;
        when(roomRequestRepo.getOne(id)).thenReturn(roomRequest);
        roomRequestService.approveRequestSecurity(id);

        verify(roomRequestRepo).getOne(id);
    }
}