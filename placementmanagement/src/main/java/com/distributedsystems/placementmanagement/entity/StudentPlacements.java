package com.distributedsystems.placementmanagement.entity;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentPlacements {
    private Placements placements;
    private List<Students> studentResult;
}
