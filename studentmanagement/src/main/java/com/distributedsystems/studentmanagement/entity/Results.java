package com.distributedsystems.studentmanagement.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Results {
    private Long resultId;
    private Long universityRollNumber;
    private int marks;
    private double cgpa;
}
