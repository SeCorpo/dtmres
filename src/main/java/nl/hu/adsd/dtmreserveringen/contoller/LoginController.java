package nl.hu.adsd.dtmreserveringen.contoller;

import nl.hu.adsd.dtmreserveringen.dto.AccountDTO;
import nl.hu.adsd.dtmreserveringen.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(originPatterns = "http://localhost:[*]")
@RestController
@RequestMapping(path = "/api/login")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final AccountService accountService;

    public LoginController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody AccountDTO accountDTO) {
        String email = accountDTO.getEmail();
        String password = accountDTO.getPassword();

        logger.info("LoginController login attempt using email: {} and password: {}", email, password);

        if(!accountService.doesAccountExist(email)) {
            logger.info("Account with email: {} does not exist", email);
            return ResponseEntity.ok(false);
        }

        if(!accountService.isAccountAdmin(email)) {
            logger.info("Account with email: {} does exist but is not an Admin", email);
            return ResponseEntity.ok(false);
        }

        if(!accountService.isPasswordCorrectForAccount(email, password)) {
            logger.info("Given password does not match");
            return ResponseEntity.ok(false);
        }

        return ResponseEntity.ok(true);
    }
}
