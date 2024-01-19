package nl.hu.adsd.dtmreserveringen.contoller;

import jakarta.validation.Valid;
import nl.hu.adsd.dtmreserveringen.dto.AccountDTO;
import nl.hu.adsd.dtmreserveringen.services.AccountDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(originPatterns = "http://localhost:[*]")
@RestController
@RequestMapping(path = "/api/auth")
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private final AccountDetailsService accountDetailsService;

    public RegistrationController(AccountDetailsService accountDetailsService) {
        this.accountDetailsService = accountDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody AccountDTO accountDTO) {
        logger.info("RegistrationController reached");

        if (accountDTO == null) {
            logger.error("Invalid request body");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request body");
        }

        //todo instead use custom catch
        if (accountDetailsService.doesAccountExist(accountDTO.username())) {
            logger.info("Username already exists: " + accountDTO.username());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        try {
            accountDetailsService.addAccount(accountDTO.username(), accountDTO.password());
            logger.info("Account registered successfully");
            return ResponseEntity.status(HttpStatus.OK).body("Account created successfully");


        //todo specify catch (check .save method)
        } catch (Exception e) {
            logger.error("Failed to register account: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register account, because of an internal server error");
        }
    }
}
