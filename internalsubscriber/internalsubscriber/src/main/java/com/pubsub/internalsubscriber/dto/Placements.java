package com.pubsub.internalsubscriber.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Placements {
    private Long placementId;
    private String companyName;
    private Long salary;
    private double minCgpa;
}
