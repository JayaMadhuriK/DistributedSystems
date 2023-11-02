package com.distributedsystems.placementmanagement.service;

import com.distributedsystems.placementmanagement.entity.Placements;
import com.distributedsystems.placementmanagement.entity.StudentPlacements;

public interface PlacementService {
    void addDetails(Placements placements);
    StudentPlacements getPlacementDetails(Long placementId);
}
