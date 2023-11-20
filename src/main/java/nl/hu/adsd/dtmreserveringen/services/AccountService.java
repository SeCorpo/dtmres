package nl.hu.adsd.dtmreserveringen.services;

import nl.hu.adsd.dtmreserveringen.entity.Account;
import nl.hu.adsd.dtmreserveringen.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    //TODO: fix json to string, or use encryption
    public boolean isPasswordCorrectForAccount(String email, String password) {
        logger.info("Checking if password is correct for account with email: {}", email);
        Account account = getAccountByEmail(email);

        String correctPassword = "\"" + account.getPassword() + "\"";

        logger.info("Correct password: {}, input password: {}", correctPassword, password);
        return correctPassword.equals(password);
    }
    public boolean isAccountAdmin(String email) {
        logger.info("Checking if account with email: {} is an admin", email);
        Account account = getAccountByEmail(email);
        return account != null && account.getAdmin() == 1;
    }

    public boolean doesAccountExist(String email) {
        logger.info("Checking if account with email: {} exists", email);
        return getAccountByEmail(email) != null;
    }
    public Account getAccountByEmail(String email) {
        logger.info("Fetching account with email: {}", email);
        return accountRepository.getAccountByEmail(email);
    }



}
