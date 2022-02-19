package com.kata.bank.services.impl;

import com.kata.bank.exceptions.InvalidTransactionException;
import com.kata.bank.exceptions.ResourceNotFoundException;
import com.kata.bank.models.Account;
import com.kata.bank.models.Operation;
import com.kata.bank.models.OperationType;
import com.kata.bank.repositories.AccountRepository;
import com.kata.bank.repositories.OperationRepository;
import com.kata.bank.services.OperationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final AccountRepository accountRepository;

    public OperationServiceImpl(OperationRepository operationRepository, AccountRepository accountRepository) {

        this.operationRepository = operationRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Operation> findAll() {

        return operationRepository.findAll();
    }

    @Override
    public void addDeposit(Double amount, Integer accountId) {

        Optional<Account> accountOpt = accountRepository.findById(accountId);
        if (!accountOpt.isPresent()) {
            throw new ResourceNotFoundException("Account not found!");
        }
        Account account = accountOpt.get();
        Double balance = account.getBalance();
        LocalDate date = LocalDate.now();
        account.setBalance(balance + amount);
        account.setLastUpdate(date);
        accountRepository.save(account);

        Operation operation = Operation.builder()
            .accountId(accountId)
            .amount(amount)
            .date(date)
            .type(OperationType.DEPOSIT)
            .build();

        operationRepository.save(operation);
    }

    @Override
    public void addWithdrawal(Double amount, Integer accountId) {

        Optional<Account> accountOpt = accountRepository.findById(accountId);
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
        accountRepository.save(account);

        Operation operation = Operation.builder()
            .accountId(accountId)
            .amount(amount)
            .date(date)
            .type(OperationType.WITHDRAWAL)
            .build();

        operationRepository.save(operation);
    }
}
