package com.kata.bank.services;

import com.kata.bank.models.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername();
}
