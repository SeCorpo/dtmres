//package nl.hu.adsd.dtmreserveringen.contoller;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import jakarta.validation.Valid;
//import nl.hu.adsd.dtmreserveringen.dto.AccountDTO;
//import nl.hu.adsd.dtmreserveringen.services.AccountDetailsService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@CrossOrigin(originPatterns = "http://localhost:[*]")
//@RestController
//@RequestMapping(path = "/api/auth")
//public class AuthController {
//
//    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
//    private final AuthenticationManager authenticationManager;
//    private final AccountDetailsService accountDetailsService;
//
//    public AuthController(AuthenticationManager authenticationManager, AccountDetailsService accountDetailsService) {
//        this.authenticationManager = authenticationManager;
//        this.accountDetailsService = accountDetailsService;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<Boolean> login(@Valid @RequestBody AccountDTO accountDTO) {
//        logger.info("AuthController reached");
//
//        if(accountDTO == null) {
//            logger.error("Request body is null");
//            return ResponseEntity.ok(false);
//        }
//
//        return ResponseEntity.ok(true);
//    }
//
//}
