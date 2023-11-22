package nl.hu.adsd.dtmreserveringen.contoller;

import nl.hu.adsd.dtmreserveringen.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@CrossOrigin(originPatterns = "http://localhost:8080/login")
@RestController
@RequestMapping(path = "/api/login")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final AccountService accountService;

    public LoginController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody Map<String, String> requestBody) {
        if(requestBody == null) {
            logger.error("Request body is null");
            return ResponseEntity.ok(false);
        }

        String email = requestBody.get("email");
        String password = requestBody.get("password");

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

        logger.info("Login positive");
        return ResponseEntity.ok(true);

    }
}
