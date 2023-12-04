package nl.hu.adsd.dtmreserveringen.contoller;

import nl.hu.adsd.dtmreserveringen.entity.Account;
import nl.hu.adsd.dtmreserveringen.repository.AccountRepository;
import nl.hu.adsd.dtmreserveringen.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(originPatterns = "http://localhost:[*]")
@RestController
@RequestMapping(path = "/api/account")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> addAccount(@RequestBody Map<String, String> requestBody) {
        if(requestBody == null) {
            logger.error("Request body is null");
            return ResponseEntity.ok(false);
        }

        int admin = 0; //Unable to make admin account on register page
        String email = requestBody.get("email");
        String password = requestBody.get("password");

        logger.info("AccountController register attempt using email: {} and password: {}", email, password);

        if(accountService.doesAccountExist(email)) {
            logger.info("Account with email: {} does already exist", email);
            return ResponseEntity.ok(false);
        }
        accountService.addAccount(admin, email, password);

        ////////////////////Check

        if(accountService.doesAccountExist(email)) {
            logger.info("Account registered successfully");
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
}