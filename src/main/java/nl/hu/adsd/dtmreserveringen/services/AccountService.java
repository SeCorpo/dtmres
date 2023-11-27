package nl.hu.adsd.dtmreserveringen.services;

import nl.hu.adsd.dtmreserveringen.entity.Account;
import nl.hu.adsd.dtmreserveringen.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean isPasswordCorrectForAccount(String email, String password) {
        logger.info("Checking if password is correct for account with email: {}", email);
        Optional<Account> optionalAccount = getAccountByEmail(email);

        return optionalAccount.map(account -> {
            String correctPassword = account.getPassword();
            return correctPassword.equals(password);
        }).orElse(false);
    }

    public boolean isAccountAdmin(String email) {
        logger.info("Checking if account with email: {} is an admin", email);
        return getAccountByEmail(email)
                .map(account -> account.getAdmin() == 1)
                .orElse(false);
    }

    public boolean doesAccountExist(String email) {
        logger.info("Checking if account with email: {} exists", email);
        return getAccountByEmail(email).isPresent();
    }

    public Optional<Account> getAccountByEmail(String email) {
        logger.info("Fetching account with email: {}", email);
        return Optional.ofNullable(accountRepository.getAccountByEmail(email));
    }
}
