package org.application.models.users;

import org.junit.Assert;
import org.junit.Test;

public class AppUserTest {

    @Test
    public void apply() {
        AppUser initialUser = new AppUser();
        initialUser.setUsername("username");
        initialUser.setPassword("password");
        initialUser.setEmail("email");
        initialUser.setAuthority("ROLE_AUTHORITY");
        initialUser.setFirstName("FN");
        initialUser.setLastName("LN");
        initialUser.setEnabled(Boolean.TRUE);

        AppUser finalUser = new AppUser();
        finalUser.apply(initialUser);

        Assert.assertEquals("username", finalUser.getUsername());
        Assert.assertEquals("password", finalUser.getPassword());
        Assert.assertEquals("email", finalUser.getEmail());
        Assert.assertEquals("ROLE_AUTHORITY", finalUser.getAuthority());
        Assert.assertEquals("FN", finalUser.getFirstName());
        Assert.assertEquals("LN", finalUser.getLastName());
        Assert.assertEquals(Boolean.TRUE, finalUser.getEnabled());
    }
}