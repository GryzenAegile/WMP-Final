package com.example.enrollment;

import java.util.List;

public class EnrollmentData {
    private List<Subject> subjects;
    private int totalCredits;

    public EnrollmentData() {
        // Default constructor required for calls to DataSnapshot.getValue(EnrollmentData.class)
    }

    public EnrollmentData(List<Subject> subjects, int totalCredits) {
        this.subjects = subjects;
        this.totalCredits = totalCredits;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public int getTotalCredits() {
        return totalCredits;
    }
}
