package com.pkch.todolist.api.todo;

public enum Priority {
    TOP(1), NORMAL(2), LATER(3);

    private int value;

    Priority(int value) {
        this.value = value;
    }
    private boolean isHigher(Priority priority){
        if (this.value < priority.value) return true;
        return false;
    }
}

