package nl.hu.adsd.dtmreserveringen.contoller;

import nl.hu.adsd.dtmreserveringen.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(originPatterns = "http://localhost:[*]")
@RestController
@RequestMapping(path = "/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final AccountService accountService;

    public AdminController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> isPasswordCorrect(@RequestBody String password) {
        String email = "admin.admin@hu.nl";
        //String email = "user@student.hu.nl"; // no admin
        //String email = "ik_besta_niet@hu.nl"; // account/email not existing

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
