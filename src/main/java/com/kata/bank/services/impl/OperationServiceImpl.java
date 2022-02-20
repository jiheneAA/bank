package com.kata.bank.services.impl;

import com.kata.bank.exceptions.InvalidTransactionException;
import com.kata.bank.exceptions.ResourceNotFoundException;
import com.kata.bank.models.Account;
import com.kata.bank.models.Operation;
import com.kata.bank.models.OperationType;
import com.kata.bank.repositories.OperationRepository;
import com.kata.bank.services.AccountService;
import com.kata.bank.services.OperationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final AccountService accountService;

    public OperationServiceImpl(OperationRepository operationRepository, AccountService accountService) {

        this.operationRepository = operationRepository;
        this.accountService = accountService;
    }

    @Override
    public List<Operation> findAll() {

        return accountService.findAll().stream()
            .map(operationRepository::findAllByAccount)
            .flatMap(List::stream)
            .collect(Collectors.toList());
    }

    @Override
    public void addDeposit(Double amount, Integer accountId) {

        Optional<Account> accountOpt = accountService.findById(accountId);
        if (!accountOpt.isPresent()) {
            throw new ResourceNotFoundException("Account not found!");
        }
        Account account = accountOpt.get();
        Double balance = account.getBalance();
        LocalDate date = LocalDate.now();
        account.setBalance(balance + amount);
        account.setLastUpdate(date);
        accountService.save(account);

        Operation operation = Operation.builder()
            .account(account)
            .amount(amount)
            .date(date)
            .type(OperationType.DEPOSIT)
            .build();

        account.addOperation(operation);
        operationRepository.save(operation);
    }

    @Override
    public void addWithdrawal(Double amount, Integer accountId) {

        Optional<Account> accountOpt = accountService.findById(accountId);
        if (!accountOpt.isPresent()) {
            throw new ResourceNotFoundException("Account not found!");
        }
        Account account = accountOpt.get();
        Double balance = account.getBalance();

        if (balance < amount) {
            throw new InvalidTransactionException(String.format("Cannot withdraw %f from this account!", amount));
        }
        LocalDate date = LocalDate.now();
        account.setBalance(balance - amount);
        account.setLastUpdate(date);
        accountService.save(account);

        Operation operation = Operation.builder()
            .account(account)
            .amount(amount)
            .date(date)
            .type(OperationType.WITHDRAWAL)
            .build();

        account.addOperation(operation);
        operationRepository.save(operation);
    }
}
