package com.kata.bank.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public enum OperationType {

    WITHDRAWAL,
    DEPOSIT;

    private static final Map<String, OperationType> enumValues = new HashMap<>();

    static {
        for (OperationType value : OperationType.values()) {
            enumValues.put(value.name(), value);
        }
    }

    @JsonCreator
    public static OperationType fromValue(String value) {

        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException("Blank value");
        }

        OperationType type = enumValues.get(value.trim().toUpperCase());
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
