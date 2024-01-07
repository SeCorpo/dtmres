package nl.hu.adsd.dtmreserveringen;

import jakarta.servlet.http.HttpServletRequest;
import nl.hu.adsd.dtmreserveringen.contoller.AuthController;
import nl.hu.adsd.dtmreserveringen.dto.AccountDTO;
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

        AccountDTO accountDTO = new AccountDTO("test@example.com", "password123");

        doNothing().when(accountDetailsService).addAccount(accountDTO.username(), accountDTO.password());

        // Call the register method
        ResponseEntity<Boolean> response = authController.register(accountDTO);

        verify(accountDetailsService, times(2)).doesAccountExist(accountDTO.username());
        verify(accountDetailsService, times(1)).addAccount(accountDTO.username(), accountDTO.password());

        assertEquals(Boolean.TRUE, response.getBody());
    }

    @Test
    void testLogin() {
        AccountDTO accountDTO = new AccountDTO("test@example.com", "password123");

        when(authenticationManager.authenticate(any())).thenReturn(null);

        // Call the login method
        ResponseEntity<Boolean> response = authController.login(accountDTO);

        verify(authenticationManager, times(1)).authenticate(any());

        assertEquals(Boolean.TRUE, response.getBody());
    }
    @Test
    void testLoginWithBadCredentials() {
        AccountDTO accountDTO = new AccountDTO("wrongemail@example.com", "password123");

        // Mocking the behavior to simulate authentication failure with wrong email
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Bad credentials"));

        ResponseEntity<Boolean> response = authController.login(accountDTO);

        verify(authenticationManager, times(1)).authenticate(any());

        assertEquals(Boolean.FALSE, response.getBody());
    }

    @RepeatedTest(value = 5, name = "{displayName} - Thread {currentRepetition}")
    void testConcurrentLogin() {
        AccountDTO accountDTO = new AccountDTO("wrongemail@example.com", "password123");

        // Mocking the behavior to simulate successful authentication
        when(authenticationManager.authenticate(any())).thenReturn(mock(Authentication.class));

        // Call the login method
        ResponseEntity<Boolean> response = authController.login(accountDTO);

        verify(authenticationManager, times(1)).authenticate(any());

        assertEquals(Boolean.TRUE, response.getBody());
    }
}
