package nl.hu.adsd.dtmreserveringen.services;

import nl.hu.adsd.dtmreserveringen.entity.Account;
import nl.hu.adsd.dtmreserveringen.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public Account loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.loadUserByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return account;
    }

    public boolean isPasswordCorrectForAccount(String email, String password) {
        logger.info("Checking if password is correct for account with email: {}", email);
        Account account = accountRepository.loadUserByUsername(email);

        return account.getPassword().equals(password);
    }

//    public boolean isAccountAdmin(String email) {
//        logger.info("Checking if account with email: {} is an admin", email);
//        return getAccountByEmail(email)
//                .map(account -> account.getAdmin() == 1)
//                .orElse(false);
//    }

    public boolean isAccountAdmin(String email) {
        logger.info("Checking if account with email: {} is an admin", email);

        Account account = loadUserByUsername(email);

        return account.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }



    public boolean doesAccountExist(String email) {
        logger.info("Checking if account with email: {} exists", email);
        return loadUserByUsername(email).isEnabled();
    }


    //REGISTER FUNCTION
    public void addAccount(int admin, String email, String password) {
        Account account = new Account();
        account.setAdmin(admin);
        account.setEmail(email);
        account.setPassword(password);

        accountRepository.save(account);
    }



}
