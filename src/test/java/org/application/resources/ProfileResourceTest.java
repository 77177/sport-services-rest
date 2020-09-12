package org.application.resources;

import org.application.models.users.AppUser;
import org.application.services.AppUserService;
import org.application.services.RoomRequestService;
import org.application.services.TrainerRequestService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProfileResourceTest {

    @Mock
    private AppUserService appUserService;

    @Mock
    private RoomRequestService roomRequestService;

    @Mock
    private TrainerRequestService trainerRequestService;

    @Spy
    @InjectMocks
    private ProfileResource profileResource;


    @Test
    public void approveRoomRequestAdmin() {
        String returnUrl = profileResource.approveRoomRequestAdmin(1L);

        Assert.assertEquals("redirect:/profile/primary", returnUrl);
    }

    @Test
    public void approveTrainerRequestAdmin() {
        String returnUrl = profileResource.approveRoomRequestAdmin(1L);

        Assert.assertEquals("redirect:/profile/primary", returnUrl);
    }

    @Test
    public void approveRoomRequestSecurity() {
        String returnUrl = profileResource.approveRoomRequestAdmin(1L);

        Assert.assertEquals("redirect:/profile/primary", returnUrl);
    }

    @Test
    public void approveTrainerRequestSecurity() {
        String returnUrl = profileResource.approveRoomRequestAdmin(1L);

        Assert.assertEquals("redirect:/profile/primary", returnUrl);
    }

    @Test
    public void getProfile() {
        AppUser appUser = mock(AppUser.class);
        ArrayList unapprovedRoomRequests = mock(ArrayList.class);
        ArrayList requestsForTrainer = mock(ArrayList.class);
        ArrayList unapprovedTrainerRequests = mock(ArrayList.class);

        when(appUserService.getCurrentUserInfo()).thenReturn(appUser);
        when(roomRequestService.getUnapprovedRequests()).thenReturn(unapprovedRoomRequests);
        when(trainerRequestService.getUnapprovedRequests()).thenReturn(unapprovedTrainerRequests);
        when(trainerRequestService.getRequestsForTrainer(any())).thenReturn(requestsForTrainer);

        ModelAndView profile = profileResource.getProfile();

        Assert.assertEquals(4, profile.getModelMap().size());
        Assert.assertEquals("profile", profile.getViewName());
    }
}