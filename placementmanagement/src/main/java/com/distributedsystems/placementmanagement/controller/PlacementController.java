package com.distributedsystems.placementmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.distributedsystems.placementmanagement.entity.Placements;
import com.distributedsystems.placementmanagement.entity.StudentPlacements;
import com.distributedsystems.placementmanagement.service.PlacementService;

@RestController
public class PlacementController {

    @Autowired
    PlacementService placementService;
    
    @PostMapping("/add")
    public void addPlacementDetails(@RequestBody Placements placements) {
        placementService.addDetails(placements);
    }
    
    @GetMapping("/{companyName}")
    public StudentPlacements getPlacementDetails(@PathVariable String companyName) {
        return placementService.getPlacementDetails(companyName);
    }
}
