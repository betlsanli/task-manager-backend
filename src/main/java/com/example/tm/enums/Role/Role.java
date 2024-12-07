package com.example.tm.enums.Role;

public enum Role {
    ASSIGNEE(1),
    REVİEWER(2);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
