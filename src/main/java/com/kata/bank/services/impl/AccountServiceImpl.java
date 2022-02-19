package com.kata.bank.services.impl;

import com.kata.bank.models.Account;
import com.kata.bank.models.User;
import com.kata.bank.repositories.AccountRepository;
import com.kata.bank.repositories.UserRepository;
import com.kata.bank.services.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {

        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Account> findAllByUserId(Integer userId) {

        User user = userRepository.getById(userId);

        return accountRepository.findAllByUser(user);
    }
}
