package nl.hu.adsd.dtmreserveringen;

import nl.hu.adsd.dtmreserveringen.contoller.AuthController;
import nl.hu.adsd.dtmreserveringen.services.AccountDetailsService;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Execution(ExecutionMode.CONCURRENT)
class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AccountDetailsService accountDetailsService;

    @InjectMocks
    private AuthController authController;

    @Test
    void testRegister() {
        when(accountDetailsService.doesAccountExist("test@example.com")).thenReturn(false, true);

        Map<String, String> registerRequest = new HashMap<>();
        registerRequest.put("email", "test@example.com");
        registerRequest.put("password", "password123");

        doNothing().when(accountDetailsService).addAccount("test@example.com", "password123");

        // Call the register method
        ResponseEntity<Boolean> response = authController.register(registerRequest);

        verify(accountDetailsService, times(2)).doesAccountExist("test@example.com");
        verify(accountDetailsService, times(1)).addAccount("test@example.com", "password123");

        assertEquals(Boolean.TRUE, response.getBody());
    }

    @Test
    void testLogin() {
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", "test@example.com");
        loginRequest.put("password", "password123");

        when(authenticationManager.authenticate(any())).thenReturn(null);

        // Call the login method
        ResponseEntity<Boolean> response = authController.login(loginRequest);

        verify(authenticationManager, times(1)).authenticate(any());

        assertEquals(Boolean.TRUE, response.getBody());
    }
    @Test
    void testLoginWithBadCredentials() {
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", "wrongemail@example.com");
        loginRequest.put("password", "password123");

        // Mocking the behavior to simulate authentication failure with wrong email
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Bad credentials"));

        ResponseEntity<Boolean> response = authController.login(loginRequest);

        verify(authenticationManager, times(1)).authenticate(any());

        assertEquals(Boolean.FALSE, response.getBody());
    }

    @RepeatedTest(value = 5, name = "{displayName} - Thread {currentRepetition}")
    void testConcurrentLogin() {
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", "test@example.com");
        loginRequest.put("password", "password123");

        // Mocking the behavior to simulate successful authentication
        when(authenticationManager.authenticate(any())).thenReturn(mock(Authentication.class));

        // Call the login method
        ResponseEntity<Boolean> response = authController.login(loginRequest);

        verify(authenticationManager, times(1)).authenticate(any());

        assertEquals(Boolean.TRUE, response.getBody());
    }
}
