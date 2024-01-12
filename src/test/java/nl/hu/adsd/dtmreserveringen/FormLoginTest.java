package nl.hu.adsd.dtmreserveringen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DtmReservationsApplication.class)
@AutoConfigureMockMvc
public class FormLoginTest {

    @Autowired
    private MockMvc mockMvc;

    //Testing if login page is loading
    @Test
    public void testLoginPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginWithInvalidCredentials() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/login")
                                .param("username", "invalidUsername")
                                .param("password", "invalidPassword")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }

    @Test
    public void testLoginWithValidCredentials() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/login")
                                .param("username", "piet@student.hu.nl")
                                .param("password", "Wachtwoord@123")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testLoginWithCustomParams() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/login")
                                .param("u", "admin")
                                .param("p", "pass")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }


}
