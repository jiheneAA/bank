package com.kata.bank.services.impl;

import com.kata.bank.exceptions.ResourceNotFoundException;
import com.kata.bank.models.Account;
import com.kata.bank.models.User;
import com.kata.bank.repositories.AccountRepository;
import com.kata.bank.services.AccountService;
import com.kata.bank.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;

    public AccountServiceImpl(AccountRepository accountRepository, UserService userService) {

        this.accountRepository = accountRepository;
        this.userService = userService;
    }

    public List<Account> findAll() {

        Optional<User> user = userService.findCurrentUser();
        if (!user.isPresent()) {
            return emptyList();
        }
        return accountRepository.findAllByUser(user.get());
    }

    public Optional<Account> findById(Integer accountId) {

        Optional<User> user = userService.findCurrentUser();

        if (!user.isPresent()) {
            throw new ResourceNotFoundException("There is no current User!");
        }
        List<Account> accounts = accountRepository.findAllByUser(user.get())
            .stream()
            .filter(account -> account.getId().equals(accountId))
            .collect(Collectors.toList());

        if (accounts.size() != 1) {
            throw new ResourceNotFoundException("Account not found!");
        }
        return Optional.ofNullable(accounts.get(0));
    }

    public void save(Account account) {

        accountRepository.save(account);
    }
}
