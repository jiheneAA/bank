package com.kata.bank.services.impl;

import com.kata.bank.models.Account;
import com.kata.bank.repositories.AccountRepository;
import com.kata.bank.services.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {

        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> findAll() {

        return accountRepository.findAll();
    }
}
