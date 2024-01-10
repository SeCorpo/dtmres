package nl.hu.adsd.dtmreserveringen.services;

import nl.hu.adsd.dtmreserveringen.entity.Account;
import nl.hu.adsd.dtmreserveringen.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountDetailsService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override //does not return an account entity, but a UserDetails object
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.getAccountByEmail(email);
        if(account == null) {
            throw new UsernameNotFoundException(email);
        }

        return new User(account.getEmail(), account.getPassword(), account.getAuthorities());
    }

    public boolean doesAccountExist(String email) {
        Optional<Account> optionalAccount = Optional.ofNullable(accountRepository.getAccountByEmail(email));
        return optionalAccount.isPresent();
    }

    public void addAccount(String email, String password) {
        Account account = new Account();
        account.setAdmin(0);
        account.setEmail(email);
        account.setPassword(passwordEncoder.encode(password));

        accountRepository.save(account);
    }


}
