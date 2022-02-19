package com.kata.bank.controllers;

import com.kata.bank.exceptions.InvalidTransactionException;
import com.kata.bank.exceptions.ResourceNotFoundException;
import com.kata.bank.models.Operation;
import com.kata.bank.services.OperationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("operations")
public class OperationController {

    private final OperationService operationService;

    public OperationController(OperationService operationService) {

        this.operationService = operationService;
    }

    @GetMapping()
    public String findOperations(Model model) {

        List<Operation> operations = operationService.findAll();
        if (operations.isEmpty()) {
            model.addAttribute("moduleName", "operations");
            return "empty";
        }
        model.addAttribute("operations", operations);
        return "operations";
    }

    @GetMapping(value = "/deposit")
    public String getDeposit(Model model) {

        model.addAttribute("accountId", "");
        model.addAttribute("amount", "");

        return "deposit";
    }

    @PostMapping(value = "/deposit")
    public String addDeposit(Model model, @ModelAttribute("amount") String amount, @ModelAttribute("accountId") String accountId) {

        try {
            operationService.addDeposit(Double.parseDouble(amount), Integer.parseInt(accountId));
        } catch (ResourceNotFoundException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "deposit";
        }
        return "redirect:/accounts/" + accountId;
    }

    @GetMapping(value = "/withdrawal")
    public String getWithdrawal(Model model) {

        model.addAttribute("accountId", "");
        model.addAttribute("amount", "");

        return "withdrawal";
    }

    @PostMapping(value = "/withdrawal")
    public String addWithdrawal(Model model, @ModelAttribute("amount") String amount, @ModelAttribute("accountId") String accountId) {

        try {
            operationService.addWithdrawal(Double.parseDouble(amount), Integer.parseInt(accountId));
        } catch (InvalidTransactionException | ResourceNotFoundException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "withdrawal";
        }
        return "redirect:/accounts/" + accountId;
    }
}
