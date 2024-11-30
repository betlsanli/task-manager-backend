package com.example.tm.enums.Priority;

public enum Priority {
    LOW(4),
    MEDIUM(3),
    HIGH(2),
    CRITICAL(1);

    private final int value;

    Priority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
