package com.example.enrollment;

import java.io.Serializable;

public class Subject implements Serializable {
    private String name;
    private int credits;

    public Subject() {
        // Default constructor required for calls to DataSnapshot.getValue(Subject.class)
    }

    public Subject(String name, int credits) {
        this.name = name;
        this.credits = credits;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }
}
