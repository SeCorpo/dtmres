package nl.hu.adsd.dtmreserveringen;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginControllerTest {

    @Test
    void testExistingAdminAccountWithCorrectPassword() {
        AccountService accountService = Mockito.mock(AccountService.class);
        Mockito.when(accountService.doesAccountExist("admin.admin@hu.nl")).thenReturn(true);
        Mockito.when(accountService.isAccountAdmin("admin.admin@hu.nl")).thenReturn(true);
        Mockito.when(accountService.isPasswordCorrectForAccount("admin.admin@hu.nl", "correctPassword")).thenReturn(true);

        LoginController loginController = new LoginController(accountService);
        Map<String, String> requestBody = Map.of("email", "admin.admin@hu.nl", "password", "correctPassword");

        ResponseEntity<Boolean> response = loginController.login(requestBody);

        assertEquals(true, response.getBody(), "If you see this the code or test is wrong because, " +
                "existing admin account with correct password can get a positive login result");
    }

    @Test
    void testExistingAdminAccountWithWrongPassword() {
        AccountService accountService = Mockito.mock(AccountService.class);
        Mockito.when(accountService.doesAccountExist("admin.admin@hu.nl")).thenReturn(true);
        Mockito.when(accountService.isAccountAdmin("admin.admin@hu.nl")).thenReturn(true);
        Mockito.when(accountService.isPasswordCorrectForAccount("admin.admin@hu.nl", "wrongPassword")).thenReturn(false);

        LoginController loginController = new LoginController(accountService);
        Map<String, String> requestBody = Map.of("email", "admin.admin@hu.nl", "password", "wrongPassword");

        ResponseEntity<Boolean> response = loginController.login(requestBody);

        assertEquals(false, response.getBody(), "If you see this the code or test is wrong because, " +
                "existing admin account with wrong password cannot get a positive login result");
    }

    @Test
    void testNotAdminAccount() {
        AccountService accountService = Mockito.mock(AccountService.class);
        Mockito.when(accountService.doesAccountExist("user@student.hu.nl")).thenReturn(true);
        Mockito.when(accountService.isAccountAdmin("user@student.hu.nl")).thenReturn(false);

        LoginController loginController = new LoginController(accountService);
        Map<String, String> requestBody = Map.of("email", "user@student.hu.nl", "password", "anyPassword");

        ResponseEntity<Boolean> response = loginController.login(requestBody);

        assertEquals(false, response.getBody(), "If you see this the code or test is wrong because, " +
                "non Admin account cannot get a positive login result");
    }

    @Test
    void testNotExistingAccount() {
        AccountService accountService = Mockito.mock(AccountService.class);
        Mockito.when(accountService.doesAccountExist("i_do_not_exist@hu.nl")).thenReturn(false);

        LoginController loginController = new LoginController(accountService);
        Map<String, String> requestBody = Map.of("email", "i_do_not_exist@hu.nl", "password", "anyPassword");

        ResponseEntity<Boolean> response = loginController.login(requestBody);

        assertEquals(false, response.getBody(), "If you see this the code or test is wrong because, " +
                "non existing account cannot get a positive login result");
    }
}
