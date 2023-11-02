package com.distributedsystems.placementmanagement.entity;

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
public class Placements {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long placementId;
    private String companyName;
    private Long salary;
    private double minCgpa;
}
