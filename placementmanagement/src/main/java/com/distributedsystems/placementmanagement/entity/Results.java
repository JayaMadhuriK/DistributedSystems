package com.distributedsystems.placementmanagement.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Results {
    private Long universityRollNumber;
    private int subject1;
    private int subject2;
    private int subject3;
    private double cgpa;
}
