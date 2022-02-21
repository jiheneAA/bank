package com.kata.bank.services.impl;

import com.kata.bank.config.CommonTestConfiguration;
import com.kata.bank.models.User;
import com.kata.bank.repositories.UserRepository;
import com.kata.bank.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserServiceImplTest.class})
@ComponentScan(
    useDefaultFilters = false,
    lazyInit = true,
    basePackageClasses = UserServiceImpl.class,
    includeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = UserServiceImpl.class)
    }
)
@WithMockUser(username = "username", authorities = {"ADMIN", "USER"})
@Import({CommonTestConfiguration.TestImportConfiguration.class})
class UserServiceImplTest extends CommonTestConfiguration {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void findAll_ok() {

        // given
        User user = User.builder()
            .id(1)
            .username("username")
            .password("password")
            .build();

        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        // when
        List<User> users = userService.findAll();

        // then
        assertThat(users).hasSize(1).containsExactly(user);
    }

    @Test
    void findAll_returnEmptyList() {

        // given
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        // when
        List<User> users = userService.findAll();

        // then
        assertThat(users).isEmpty();
    }

    @Test
    void findCurrentUser_ok() {

        User user = User.builder()
            .id(1)
            .username("username")
            .password("password")
            .build();

        // given
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));

        // when
        Optional<User> users = userService.findCurrentUser();

        // then
        assertThat(users).isPresent().contains(user);
    }

    @Test
    void findCurrentUser_empty() {

        // given
        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());

        // when
        Optional<User> users = userService.findCurrentUser();

        // then
        assertThat(users).isEmpty();
    }
}