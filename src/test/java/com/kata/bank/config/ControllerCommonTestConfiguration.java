package com.kata.bank.config;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebAppConfiguration
public abstract class ControllerCommonTestConfiguration extends CommonTestConfiguration {

    @Autowired
    protected WebApplicationContext webApplicationContext;
    protected MockMvc mockMvc;

    public ControllerCommonTestConfiguration() {

    }

    @BeforeEach
    public void setUp() {

        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .alwaysDo(print())
            .build();
    }

    @Configuration
    public static class ControllerTestConfiguration {

    }
}
