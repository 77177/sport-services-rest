package org.application.services;

import org.application.repositories.RoomRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class RoomServiceTest {

    @Mock
    private RoomRepo roomRepo;

    @Spy
    @InjectMocks
    private RoomService roomService;

    @Test
    public void getAllRooms() {
        roomService.getAllRooms();

        Mockito.verify(roomRepo).findAll();
    }
}