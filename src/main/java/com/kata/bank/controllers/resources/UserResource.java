package com.kata.bank.controllers.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kata.bank.models.Account;
import com.kata.bank.models.User;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserResource {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private List<String> accountNumbers;

    private UserResource() {

    }

    public UserResource(String username, String firstName, String lastName, String email, LocalDate birthDate, List<String> accountNumbers) {

        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.accountNumbers = accountNumbers;
    }

    public UserResource(User user) {

        this(user.getUsername(),
            user.getUsername(),
            user.getLastName(),
            user.getEmail(),
            user.getBirthDate(),
            user.getAccounts().stream().map(Account::getNumber).distinct().collect(Collectors.toList()));
    }

    public String getUsername() {

        return username;
    }

    public String getFirstName() {

        return firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public String getEmail() {

        return email;
    }

    public LocalDate getBirthDate() {

        return birthDate;
    }

    public List<String> getAccountNumbers() {

        return accountNumbers;
    }
}
