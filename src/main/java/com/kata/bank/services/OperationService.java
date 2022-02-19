package com.kata.bank.services;

import com.kata.bank.models.Operation;

import java.util.List;

public interface OperationService {

    List<Operation> findAll();

    void addDeposit(Double amount, Integer accountId);

    void addWithdrawal(Double amount, Integer accountId);
}
