package nl.hu.adsd.dtmreserveringen.contoller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import nl.hu.adsd.dtmreserveringen.dto.AccountDTO;
import nl.hu.adsd.dtmreserveringen.services.AccountDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(originPatterns = "http://localhost:[*]")
@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final AccountDetailsService accountDetailsService;

    public AuthController(AuthenticationManager authenticationManager, AccountDetailsService accountDetailsService) {
        this.authenticationManager = authenticationManager;
        this.accountDetailsService = accountDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@Valid @RequestBody AccountDTO accountDTO) {
        logger.info("AuthController reached");

        if(accountDTO == null) {
            logger.error("Request body is null");
            return ResponseEntity.ok(false);
        }
        if(accountDetailsService.doesAccountExist(accountDTO.username())) {
            return ResponseEntity.ok(false);
        }

        try {
            accountDetailsService.addAccount(accountDTO.username(), accountDTO.password());
        } catch(Exception e) {
            logger.error("Unable to register account: ", e);
            return ResponseEntity.ok(false);
        }

        //check if account is made successfully
        if(accountDetailsService.doesAccountExist(accountDTO.username())) {
            logger.info("Account registered successfully");
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.ok(false);
    }


    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@Valid @RequestBody AccountDTO accountDTO) {
        logger.info("AuthController reached");

        if(accountDTO == null) {
            logger.error("Request body is null");
            return ResponseEntity.ok(false);
        }

        logger.info("AccountDTO: " + accountDTO.toString());
//        logger.info("HttpServletRequest request: " + request.toString());

//        try {
//            Authentication authenticationReq =
//                    UsernamePasswordAuthenticationToken.unauthenticated(
//                            accountDTO.getUsername(),
//                            accountDTO.getPassword()
//                    );
//
//            Authentication authenticationRes =
//                    this.authenticationManager.authenticate(authenticationReq);
//
//            logger.info(authenticationReq.toString());
//            logger.info(authenticationRes.toString());
//
//            SecurityContextHolder.getContext().setAuthentication(authenticationRes);
////            HttpSession session = request.getSession(true);

            return ResponseEntity.ok(true);
//        } catch (AuthenticationException e) {
//            logger.error("Authentication failed: " + e.getMessage());
//            return ResponseEntity.ok(false);
//        }
    }

}
