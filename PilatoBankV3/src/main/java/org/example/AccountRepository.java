package org.example;

import java.util.Optional;

public interface AccountRepository {

    // 1. Contract to save a brand new bank account to the storage system
    void save(Account account);

    // 2. Contract to search for an account using its unique account number
    // We use Optional to handle cases safely where an account might not exist!
    Optional<Account> findByAccountNumber(String accountNumber);

    // 3. Contract to update an existing user's balance after a deposit or withdrawal
    void updateBalance(String accountNumber, double newBalance);
}