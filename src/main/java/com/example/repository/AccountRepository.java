package com.example.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    /**
     * Finds an account by username
     * @param username the username to be searched for
     * @return an optional of an Account. Will  not be present if not found
     */
    Optional<Account> findByUsername(String username);

    /**
     * Finds an account by its username and password combination
     * @param username the username to be searched for
     * @param Password the password of the specified username
     * @return an optional of an Account. Will not be present if not found
     */
    Optional<Account> findByUsernameAndPassword(String username, String Password);
}
