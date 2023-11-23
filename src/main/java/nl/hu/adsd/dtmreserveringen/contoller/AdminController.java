package nl.hu.adsd.dtmreserveringen.contoller;

import nl.hu.adsd.dtmreserveringen.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@CrossOrigin(originPatterns = "http://localhost:[*]")
@RestController
@RequestMapping(path = "/admin")
public class AdminController {
    public void setEmail(String email) {
        this.email = email;
    }

    public String email = "admin.admin@hu.nl";

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final AccountService accountService;

    public AdminController(AccountService accountService) {
        this.accountService = accountService;
    }
/*
    @PostMapping("/encrypt")
            
        String originalString = "hello";
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(
                originalString.getBytes(StandardCharsets.UTF_8));
        String sha256hex = new String(Hex.encode(hash));
*/

    @PostMapping("/login")
    public ResponseEntity<Boolean> isPasswordCorrect(@RequestBody String password) {

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
