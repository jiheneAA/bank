package com.kata.bank.repositories;

import com.kata.bank.models.Account;
import com.kata.bank.models.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Integer> {

}
