package com.kata.bank.services;

import com.kata.bank.models.Account;

import java.util.List;

public interface AccountService {

    List<Account> findAllByUserId(Integer userId);
}
