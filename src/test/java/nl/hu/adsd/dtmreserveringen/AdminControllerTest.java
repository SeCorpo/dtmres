package nl.hu.adsd.dtmreserveringen;

import nl.hu.adsd.dtmreserveringen.services.AccountService;
import nl.hu.adsd.dtmreserveringen.contoller.AdminController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class AdminControllerTest {

    @Test
    void testExistingAdminAccountWithCorrectPassword() {
        // Arrange
        AccountService accountService = Mockito.mock(AccountService.class);
        Mockito.when(accountService.doesAccountExist("admin.admin@hu.nl")).thenReturn(true);
        Mockito.when(accountService.isAccountAdmin("admin.admin@hu.nl")).thenReturn(true);
        Mockito.when(accountService.isPasswordCorrectForAccount("admin.admin@hu.nl", "correctPassword")).thenReturn(true);

        AdminController adminController = new AdminController(accountService);
        adminController.setEmail("admin.admin@hu.nl");
        ResponseEntity<Boolean> response = adminController.isPasswordCorrect("correctPassword");

        assertEquals(Boolean.TRUE, response.getBody());
    }

    @Test
    void testExistingAdminAccountWithWrongPassword() {
        // Arrange
        AccountService accountService = Mockito.mock(AccountService.class);
        Mockito.when(accountService.doesAccountExist("admin.admin@hu.nl")).thenReturn(true);
        Mockito.when(accountService.isAccountAdmin("admin.admin@hu.nl")).thenReturn(true);
        Mockito.when(accountService.isPasswordCorrectForAccount("admin.admin@hu.nl", "wrongPassword")).thenReturn(false);

        AdminController adminController = new AdminController(accountService);
        adminController.setEmail("admin.admin@hu.nl");
        ResponseEntity<Boolean> response = adminController.isPasswordCorrect("wrongPassword");

        assertNotEquals(Boolean.TRUE, response.getBody());
    }

    @Test
    void testNotAdminAccount() {
        // Arrange
        AccountService accountService = Mockito.mock(AccountService.class);
        Mockito.when(accountService.doesAccountExist("user@student.hu.nl")).thenReturn(true);
        Mockito.when(accountService.isAccountAdmin("user@student.hu.nl")).thenReturn(false);

        AdminController adminController = new AdminController(accountService);
        adminController.setEmail("user@student.hu.nl");
        ResponseEntity<Boolean> response = adminController.isPasswordCorrect("anyPassword");

        assertNotEquals(Boolean.TRUE, response.getBody());
    }

    @Test
    void testNotExistingAccount() {
        // Arrange
        AccountService accountService = Mockito.mock(AccountService.class);
        Mockito.when(accountService.doesAccountExist("i_do_not_exits@hu.nl")).thenReturn(false);

        AdminController adminController = new AdminController(accountService);
        adminController.setEmail("i_do_not_exits@hu.nl");
        ResponseEntity<Boolean> response = adminController.isPasswordCorrect("anyPassword");

        assertNotEquals(Boolean.TRUE, response.getBody());
    }
}
