package com.kata.bank.controllers;

import com.kata.bank.models.Account;
import com.kata.bank.services.AccountService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {

        this.accountService = accountService;
    }

    @GetMapping()
    public String findAccounts(Model model) {

        List<Account> accounts = accountService.findAll();
        if (CollectionUtils.isEmpty(accounts)) {
            model.addAttribute("moduleName", "accounts");
            return "empty";
        }
        model.addAttribute("accounts", accounts);
        return "accounts";
    }
}
