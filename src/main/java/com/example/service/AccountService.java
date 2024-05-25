package com.example.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Checks if an account exists for the specified username
     * @param username the username to be searched for in the database
     * @return true if account exists with that username or false otherwise
     */
    public Boolean exists(String username) {    
        Optional<Account> account = accountRepository.findByUsername(username);
        return account.isPresent();
    }

    /**
     * Checks if an account exists for the specified id
     * @param username the id to be searched for in the database
     * @return true if account exists with that id or false otherwise
     */
    public Boolean exists(Integer id) {    
        Optional<Account> account = accountRepository.findById(id);
        return account.isPresent();
    }

    /**
     * Saves an account to the database
     * @param account account to be persisted to the database
     * @return the new account record including its id
     */
    public Account register(Account account) {
        return accountRepository.save(account);
    }

    /**
     * Checks if an account exists with the combination of username and password
     * @param username the username to be searched for
     * @param password the password to the specified username
     * @return an optional of the account searched for. Will not be present if account not found
     */
    public Optional<Account> login(String username, String password) {
        return accountRepository.findByUsernameAndPassword(username, password);
    }
}
