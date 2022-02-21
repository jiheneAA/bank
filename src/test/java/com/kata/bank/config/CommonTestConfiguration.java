package com.kata.bank.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@AutoConfigureWebMvc
public abstract class CommonTestConfiguration {

    public CommonTestConfiguration() {

    }

    @BeforeEach
    public void init() {

        MockitoAnnotations.openMocks(this);
    }

    @Import({JacksonAutoConfiguration.class})
    public static class TestImportConfiguration {

    }
}
