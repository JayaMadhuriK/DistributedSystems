package com.distributedsystems.resultmanagement.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentResult {
    private Long universityRollNumber;
    private String firstName;
    private String lastName;
    private String contactDetails;
    @Enumerated(EnumType.STRING)
    private Course course;
    @Enumerated(EnumType.STRING)
    private Year year;
    private int marks;
    private double cgpa;
}
