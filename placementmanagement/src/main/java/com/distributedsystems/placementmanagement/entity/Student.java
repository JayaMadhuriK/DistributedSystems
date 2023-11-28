package com.distributedsystems.placementmanagement.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Student {
    private Long universityRollNumber;
    private String firstName;
    private String lastName;
    private String contactDetails;
    @Enumerated(EnumType.STRING)
    private Course course;
    @Enumerated(EnumType.STRING)
    private Year year;
    private double cgpa;
}
