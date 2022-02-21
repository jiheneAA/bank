package com.kata.bank.services.impl;

import com.kata.bank.config.CommonTestConfiguration;
import com.kata.bank.exceptions.ResourceNotFoundException;
import com.kata.bank.models.Account;
import com.kata.bank.models.User;
import com.kata.bank.repositories.AccountRepository;
import com.kata.bank.services.AccountService;
import com.kata.bank.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {AccountServiceImplTest.class})
@ComponentScan(
    useDefaultFilters = false,
    lazyInit = true,
    basePackageClasses = AccountServiceImpl.class,
    includeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AccountServiceImpl.class)
    }
)
@Import({CommonTestConfiguration.TestImportConfiguration.class})
class AccountServiceImplTest extends CommonTestConfiguration {

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Test
    void findAll_shouldReturnAllAccountsByUser() {

        // given
        Account account = Account.builder().id(1).number("1234566789").balance(1000.0).build();
        User user = User.builder()
            .id(1)
            .username("username")
            .password("password")
            .accounts(Collections.singletonList(account))
            .build();

        when(userService.findCurrentUser()).thenReturn(Optional.of(user));
        when(accountRepository.findAllByUser(user)).thenReturn(Collections.singletonList(account));

        // when
        List<Account> accounts = accountService.findAll();

        // then
        assertThat(accounts).hasSize(1);
        assertThat(accounts.get(0)).isEqualTo(account);
        verify(userService, only()).findCurrentUser();
        verify(accountRepository, only()).findAllByUser(user);
    }

    @Test
    void findAll_shouldReturnEmptyList_whenNoCurrentUser() {

        // given
        when(userService.findCurrentUser()).thenReturn(Optional.empty());

        // when
        List<Account> accounts = accountService.findAll();

        // then
        assertThat(accounts).isEmpty();
        verify(userService, only()).findCurrentUser();
    }

    @Test
    void findById_shouldReturnOnlyOneAccount() {

        // given
        Account expectedAccount = Account.builder().id(1).number("1234566789").balance(1000.0).build();
        User user = User.builder()
            .id(1)
            .username("username")
            .password("password")
            .accounts(Collections.singletonList(expectedAccount))
            .build();

        when(userService.findCurrentUser()).thenReturn(Optional.of(user));
        when(accountRepository.findAllByUser(user)).thenReturn(Collections.singletonList(expectedAccount));

        // when
        Optional<Account> account = accountService.findById(1);

        // then
        assertThat(account).isPresent().contains(expectedAccount);
        verify(userService, only()).findCurrentUser();
        verify(accountRepository, only()).findAllByUser(user);
    }

    @Test
    void findById_shouldThrowResourceNotFoundException_whenNoCurrentUser() {

        // given
        when(userService.findCurrentUser()).thenReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> accountService.findById(1))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("There is no current User!");
    }

    @Test
    void findById_shouldThrowResourceNotFoundException_whenAccountNotFound() {

        // given
        User user = User.builder()
            .id(1)
            .username("username")
            .password("password")
            .build();

        when(userService.findCurrentUser()).thenReturn(Optional.of(user));
        when(accountRepository.findAllByUser(user)).thenReturn(Collections.emptyList());

        // when // then
        assertThatThrownBy(() -> accountService.findById(1))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Account not found!");
    }

    @Test
    void findById_shouldThrowException_whenManyAccountsWithSameId() {

        // given
        Account account1 = Account.builder().id(1).number("1234566789").balance(1000.0).build();
        Account account2 = Account.builder().id(1).number("1111111111").balance(500.0).build();
        User user = User.builder()
            .id(1)
            .username("username")
            .password("password")
            .accounts(Arrays.asList(account1, account2))
            .build();

        when(userService.findCurrentUser()).thenReturn(Optional.of(user));
        when(accountRepository.findAllByUser(user)).thenReturn(Arrays.asList(account1, account2));

        // when // then
        assertThatThrownBy(() -> accountService.findById(1))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Account not found!");
    }

    @Test
    void save_ok() {

        // given
        Account account = Account.builder().id(1).number("1234566789").balance(1000.0).build();

        // when
        accountService.save(account);

        // then
        verify(accountRepository, only()).save(account);
    }

}