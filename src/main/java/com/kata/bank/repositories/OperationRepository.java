package com.kata.bank.repositories;

import com.kata.bank.models.Account;
import com.kata.bank.models.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Integer> {

    List<Operation> findAllByAccount(Account account);
}
