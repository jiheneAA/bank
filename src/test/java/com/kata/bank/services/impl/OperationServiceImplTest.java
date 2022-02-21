package com.kata.bank.services.impl;

import com.kata.bank.config.CommonTestConfiguration;
import com.kata.bank.exceptions.InvalidTransactionException;
import com.kata.bank.exceptions.ResourceNotFoundException;
import com.kata.bank.models.Account;
import com.kata.bank.models.Operation;
import com.kata.bank.models.OperationType;
import com.kata.bank.repositories.OperationRepository;
import com.kata.bank.services.AccountService;
import com.kata.bank.services.OperationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {OperationServiceImplTest.class})
@ComponentScan(
    useDefaultFilters = false,
    lazyInit = true,
    basePackageClasses = OperationServiceImpl.class,
    includeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = OperationServiceImpl.class)
    }
)
@Import({CommonTestConfiguration.TestImportConfiguration.class})
class OperationServiceImplTest extends CommonTestConfiguration {

    @MockBean
    private OperationRepository operationRepository;

    @MockBean
    private AccountService accountService;

    @Autowired
    private OperationService operationService;

    @Test
    void findAll_ok() {

        // given
        String accountNumber = "1234566789";
        Account account = Account.builder().id(1).number(accountNumber).balance(1000.0).build();

        Operation operation1 = Operation.builder()
            .account(account)
            .amount(500.0)
            .date(LocalDate.now())
            .type(OperationType.DEPOSIT)
            .build();

        Operation operation2 = Operation.builder()
            .account(account)
            .amount(250.0)
            .date(LocalDate.now())
            .type(OperationType.WITHDRAWAL)
            .build();

        when(accountService.findAll()).thenReturn(Collections.singletonList(account));
        when(operationRepository.findAllByAccount(account)).thenReturn(Arrays.asList(operation1, operation2));

        // when
        List<Operation> operations = operationService.findAll();

        assertThat(operations).hasSize(2).containsExactlyInAnyOrder(operation1, operation2);
    }

    @Test
    void findAll_emptyList_whenNoAccount() {

        // given
        when(accountService.findAll()).thenReturn(Collections.emptyList());

        // when
        List<Operation> operations = operationService.findAll();

        assertThat(operations).isEmpty();
    }

    @Test
    void findAll_emptyList_whenNoOperations() {

        // given
        String accountNumber = "1234566789";
        Account account = Account.builder().id(1).number(accountNumber).balance(1000.0).build();

        when(accountService.findAll()).thenReturn(Collections.singletonList(account));
        when(operationRepository.findAllByAccount(account)).thenReturn(Collections.emptyList());

        // when
        List<Operation> operations = operationService.findAll();

        assertThat(operations).isEmpty();
    }

    @Test
    void addDeposit_ok() {

        // given
        String accountNumber = "1234566789";
        Account account = Account.builder().id(1).number(accountNumber).balance(1000.0).build();

        when(accountService.findById(1)).thenReturn(Optional.of(account));

        Account expectedAccount = Account.builder().id(1).number(accountNumber).balance(950.0).build();

        // when
        operationService.addDeposit(50.0, 1);

        Operation operation = Operation.builder()
            .account(expectedAccount)
            .amount(50.0)
            .date(LocalDate.now())
            .type(OperationType.DEPOSIT)
            .build();

        // then
        verify(operationRepository, only()).save(operation);
    }

    @Test
    void addDeposit_throwException_whenAccountNotFound() {

        // given
        when(accountService.findById(1)).thenReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> operationService.addDeposit(50.0, 1))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Account not found!");
    }

    @Test
    void addWithdrawal_ok() {

        // given
        String accountNumber = "1234566789";
        Account account = Account.builder().id(1).number(accountNumber).balance(1000.0).build();

        when(accountService.findById(1)).thenReturn(Optional.of(account));

        Account expectedAccount = Account.builder().id(1).number(accountNumber).balance(1050.0).build();

        // when
        operationService.addWithdrawal(50.0, 1);

        Operation operation = Operation.builder()
            .account(expectedAccount)
            .amount(50.0)
            .date(LocalDate.now())
            .type(OperationType.WITHDRAWAL)
            .build();

        // then
        verify(operationRepository, only()).save(operation);
    }

    @Test
    void addWithdrawal_throwException_whenAccountNotFound() {

        // given
        when(accountService.findById(1)).thenReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> operationService.addWithdrawal(50.0, 1))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Account not found!");
    }

    @Test
    void addWithdrawal_throwException_whenAmountMoreThanBalance() {

        // given
        Account account = Account.builder().id(1).number("1234566789").balance(1000.0).build();

        when(accountService.findById(1)).thenReturn(Optional.of(account));

        // when // then
        assertThatThrownBy(() -> operationService.addWithdrawal(1500.0, 1))
            .isInstanceOf(InvalidTransactionException.class)
            .hasMessageContaining("Cannot withdraw");
    }
}