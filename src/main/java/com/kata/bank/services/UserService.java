package com.kata.bank.services;

import com.kata.bank.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findCurrentUser();
}
