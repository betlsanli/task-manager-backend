package com.example.todo.enums.Status;

public enum Status {
    TO_DO(1),
    IN_PROGRESS(2),
    DONE(3);

    private int value;

    private Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
