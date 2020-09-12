package org.application;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AppTest {

    @Autowired
    MockMvc mockMvc;

    @Before
    public void loaded() {
        assertNotNull(mockMvc);
    }

    @Test
    public void roomBookingTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/services/rooms/apply")
                .with(user("TRAINER").password("TEST").roles("TRAINER"))
                .with(csrf().asHeader())
                .param("id", "1")
                .param("start", "2000-01-01T13:00")
                .param("end", "2000-01-01T18:00"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/services/rooms"));

        //Альтернатива
        mockMvc.perform(MockMvcRequestBuilders.get("/services/rooms/apply")
                .with(user("TRAINER").password("TEST").roles("TRAINER"))
                .with(csrf().asHeader())
                .param("id", "1")
                .param("start", "2000-01-01T13:00")
                .param("end", "2100-01-01T18:00"))
                .andExpect(MockMvcResultMatchers.view().name("rooms"));
    }

    @Test
    public void signUpToTrainer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/services/trainers/apply")
                .with(user("LEARNER").password("TEST").roles("USER"))
                .with(csrf().asHeader())
                .param("id", "1")
                .param("start", "2000-01-01T13:00")
                .param("end", "2000-01-01T18:00"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/services/trainers"));

        //Альтернатива
        mockMvc.perform(MockMvcRequestBuilders.get("/services/trainers/apply")
                .with(user("LEARNER").password("TEST").roles("USER"))
                .with(csrf().asHeader())
                .param("id", "1")
                .param("start", "2000-01-01T13:00")
                .param("end", "2100-01-01T18:00"))
                .andExpect(MockMvcResultMatchers.view().name("trainers"));
    }

    @Test
    public void acknowledgeByAdminOfRoomRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/rooms/approve")
                .with(user("ADMIN").password("TEST").roles("ADMIN"))
                .with(csrf().asHeader())
                .param("id", "1"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/profile/primary"));

        //Альтернатива
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/rooms/reject")
                .with(user("ADMIN").password("TEST").roles("ADMIN"))
                .with(csrf().asHeader())
                .param("id", "1"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/profile/primary"));
    }

    @Test
    public void acknowledgeByTrainerOfTrainerRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/trainers/approve")
                .with(user("TRAINER").password("TEST").roles("TRAINER"))
                .with(csrf().asHeader())
                .param("id", "1"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/profile/primary"));

        //Альтернатива
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/trainers/reject")
                .with(user("TRAINER").password("TEST").roles("TRAINER"))
                .with(csrf().asHeader())
                .param("id", "1"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/profile/primary"));
    }

    @Test
    public void acknowledgeBySecurityOfRoomRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/rooms/approve/sec")
                .with(user("SECURITY").password("TEST").roles("SECURITY"))
                .with(csrf().asHeader())
                .param("id", "2"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/profile/primary"));

        //Альтернатива
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/rooms/reject")
                .with(user("SECURITY").password("TEST").roles("SECURITY"))
                .with(csrf().asHeader())
                .param("id", "2"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/profile/primary"));
    }

    @Test
    public void acknowledgeBySecurityOfTrainerRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/trainers/approve/sec")
                .with(user("SECURITY").password("TEST").roles("SECURITY"))
                .with(csrf().asHeader())
                .param("id", "2"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/profile/primary"));

        //Альтернатива
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/trainers/reject")
                .with(user("SECURITY").password("TEST").roles("SECURITY"))
                .with(csrf().asHeader())
                .param("id", "2"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/profile/primary"));
    }
}
