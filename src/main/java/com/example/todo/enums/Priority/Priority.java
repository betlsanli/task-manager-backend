package com.example.todo.enums.Priority;

public enum Priority {
    LOW(4),
    MEDIUM(3),
    HIGH(2),
    CRITICAL(1);

    private int value;

    private Priority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
