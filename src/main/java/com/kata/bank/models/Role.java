package com.kata.bank.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public enum Role {

    USER,
    ADMIN;

    private static final Map<String, Role> enumValues = new HashMap<>();

    static {
        for (Role value : Role.values()) {
            enumValues.put(value.name(), value);
        }
    }

    @JsonCreator
    public static Role fromValue(String value) {

        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException("Blank value");
        }

        Role type = enumValues.get(value.trim().toUpperCase());
        if (type == null) {
            throw new IllegalArgumentException(String.format("Cant't find [%s] in DataProvider values", value));
        }

        return type;
    }

    public static boolean isValid(String value) {

        if (StringUtils.isBlank(value)) {
            return false;
        }

        return enumValues.containsKey(value.trim().toUpperCase());
    }
}
