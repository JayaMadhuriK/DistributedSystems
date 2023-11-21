package com.distributedsystems.resultmanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Results {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long resultId;
    private Long universityRollNumber;
    private int marks;
    private double cgpa;
}
