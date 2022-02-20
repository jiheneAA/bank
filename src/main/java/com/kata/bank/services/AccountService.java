package com.kata.bank.services;

import com.kata.bank.models.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    List<Account> findAll();

    Optional<Account> findById(Integer accountId);

    void save(Account account);
}
