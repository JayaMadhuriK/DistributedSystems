package com.distributedsystems.resultmanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Results {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long resultId;
    private Long universityRollNumber;
    private int subject1;
    private int subject2;
    private int subject3;
    private double cgpa;
}
