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

    public Boolean exists(String username) {    
        Optional<Account> account = accountRepository.findByUsername(username);
        return account.isPresent();
    }
    public Boolean exists(Integer id) {    
        Optional<Account> account = accountRepository.findById(id);
        return account.isPresent();
    }
    public Account register(Account account) {
        return accountRepository.save(account);
    }
    public Optional<Account> login(String username, String password) {
        return accountRepository.findByUsernameAndPassword(username, password);
    }
}
