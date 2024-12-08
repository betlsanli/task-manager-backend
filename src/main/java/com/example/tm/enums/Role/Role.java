package com.example.tm.enums.Role;

public enum Role {
    DEVELOPER(1),
    MANAGER(2);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
