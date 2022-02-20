package com.kata.bank.services.impl;

import com.kata.bank.models.User;
import com.kata.bank.repositories.UserRepository;
import com.kata.bank.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {

        return userRepository.findAll();
    }

    @Override
    public Optional<User> findCurrentUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof UserDetails)) {
            return Optional.empty();
        }
        String username = ((UserDetails) principal).getUsername();

        return userRepository.findByUsername(username);
    }
}
