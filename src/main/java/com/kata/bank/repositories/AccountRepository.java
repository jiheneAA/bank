package com.kata.bank.repositories;

import com.kata.bank.models.Account;
import com.kata.bank.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    List<Account> findAllByUser(User user);
}