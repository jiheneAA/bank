package com.kata.bank.controllers;

import com.kata.bank.controllers.resources.UserResource;
import com.kata.bank.exceptions.ResourceNotFoundException;
import com.kata.bank.models.User;
import com.kata.bank.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResource>> findAllUsers() {

        return ResponseEntity.ok(userService.findAll().stream()
            .map(UserResource::new)
            .distinct()
            .collect(Collectors.toList()));
    }

    @GetMapping(value = "/current-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> findCurrentUser() {

        User user = userService.findCurrentUser().orElseThrow(() -> new ResourceNotFoundException("There is no current User!"));
        return ResponseEntity.ok(new UserResource(user));
    }
}
