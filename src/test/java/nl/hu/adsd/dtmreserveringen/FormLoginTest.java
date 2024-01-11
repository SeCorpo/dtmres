package nl.hu.adsd.dtmreserveringen;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SecurityConfig.class)
@AutoConfigureMockMvc
public class FormLoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType("text/html;charset=UTF-8"))
                .andExpect(header().string("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate"))
                .andExpect(header().string("Pragma", "no-cache"))
                .andExpect((ResultMatcher) content().string(containsString("j_username")));
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
                .andExpect(redirectedUrl("/"));
    }


}
