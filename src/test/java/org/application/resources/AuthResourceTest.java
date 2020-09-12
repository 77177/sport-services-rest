package org.application.resources;

import org.application.models.users.AppUser;
import org.application.services.AppUserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthResourceTest {

    @Mock
    private AppUserService appUserService;

    @Spy
    @InjectMocks
    private AuthResource authResource;


    @Test
    public void getLogin() {
        String login = authResource.getLogin();

        Assert.assertEquals("login", login);
    }

    @Test
    public void getLoginError() {
        String loginError = authResource.getLoginError();

        Assert.assertEquals("login-error", loginError);
    }

    @Test
    public void register() {
        when(appUserService.createUser(Mockito.any())).thenReturn(1L);

        AppUser appUser = Mockito.mock(AppUser.class);

        String login = authResource.register(appUser);

        Assert.assertEquals("redirect:login", login);
    }

    @Test
    public void registerError() {
        when(appUserService.createUser(Mockito.any())).thenThrow(new IllegalArgumentException("Broken role"));

        AppUser appUser = Mockito.mock(AppUser.class);

        String loginError = authResource.register(appUser);

        Assert.assertEquals("forward:login-error", loginError);
    }

    @Test
    public void registerForm() {
        String registerForm = authResource.registerForm();

        Assert.assertEquals("register", registerForm);
    }
}