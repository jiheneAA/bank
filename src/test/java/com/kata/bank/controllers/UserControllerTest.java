package com.kata.bank.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kata.bank.config.CommonTestConfiguration;
import com.kata.bank.config.ControllerCommonTestConfiguration;
import com.kata.bank.controllers.resources.UserResource;
import com.kata.bank.models.User;
import com.kata.bank.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ContextConfiguration(classes = {UserControllerTest.class})
@ComponentScan(
    useDefaultFilters = false,
    lazyInit = true,
    basePackageClasses = UserController.class,
    includeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = UserController.class)
    }
)
@Import({CommonTestConfiguration.TestImportConfiguration.class})
class UserControllerTest extends ControllerCommonTestConfiguration {

    private static final String USERS_URL = "/users";
    private static final String CURRENT_USER_URL = "/users/current-user";

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAllUsers_ok() throws Exception {

        // Given
        User user1 = User.builder().id(1).username("jiheneAA").password("jihene_password").build();
        User user2 = User.builder().id(2).username("soat").password("soat_password").build();

        when(userService.findAll()).thenReturn(Arrays.asList(user1, user2));

        // When
        MockHttpServletResponse response = mockMvc
            .perform(get(USERS_URL))
            .andReturn()
            .getResponse();

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        String body = response.getContentAsString(StandardCharsets.UTF_8);
        List<UserResource> users = Arrays.asList(objectMapper.readValue(body, UserResource[].class));
        assertThat(users.get(0).getUsername()).isEqualTo("jiheneAA");
        assertThat(users.get(1).getUsername()).isEqualTo("soat");

        verify(userService, only()).findAll();
    }

    @Test
    void findAllUsers_returnEmptyList() throws Exception {

        // Given
        when(userService.findAll()).thenReturn(Collections.emptyList());

        // When
        MockHttpServletResponse response = mockMvc
            .perform(get(USERS_URL))
            .andReturn()
            .getResponse();

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        String body = response.getContentAsString(StandardCharsets.UTF_8);
        List<UserResource> users = Arrays.asList(objectMapper.readValue(body, UserResource[].class));
        assertThat(users).isEmpty();

        verify(userService, only()).findAll();
    }

    @Test
    void findCurrentUser_ok() throws Exception {

        // Given
        User user = User.builder().id(1).username("jiheneAA").password("jihene_password").build();

        when(userService.findCurrentUser()).thenReturn(Optional.of(user));

        // When
        MockHttpServletResponse response = mockMvc
            .perform(get(CURRENT_USER_URL))
            .andReturn()
            .getResponse();

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        String body = response.getContentAsString(StandardCharsets.UTF_8);
        UserResource currentUser = objectMapper.readValue(body, UserResource.class);
        assertThat(currentUser.getUsername()).isEqualTo("jiheneAA");

        verify(userService, only()).findCurrentUser();
    }
}