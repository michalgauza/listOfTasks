package com.example.listoftasks.models;

public enum TaskStatus {
    OPEN("OPEN"), TRAVELING("TRAVELING"), WORKING("WORKING");

    public final String label;

    TaskStatus(String label) {
        this.label = label;
    }
}
