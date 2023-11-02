package com.distributedsystems.placementmanagement.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.distributedsystems.placementmanagement.entity.Placements;
import com.distributedsystems.placementmanagement.entity.StudentPlacements;
import com.distributedsystems.placementmanagement.entity.Students;
import com.distributedsystems.placementmanagement.repository.PlacementsRepository;
import com.distributedsystems.placementmanagement.service.PlacementService;

@Service
public class PlacementServiceImplementation implements PlacementService{

    @Autowired
    PlacementsRepository placementRepo;
    @Autowired
    WebClient webClient;
    
    @Override
    public void addDetails(Placements placements) {
        placementRepo.save(placements);
    }

    @Override
    public StudentPlacements getPlacementDetails(Long placementId) {
        Optional<Placements> placements = placementRepo.findById(placementId);
        List<Students> students = webClient
                .get()
                .uri("http://localhost:6062/result/"+placements.get().getMinCgpa())
                .retrieve()
                .bodyToFlux(Students.class)
                .collectList()
                .block();
        StudentPlacements studentPlacements = new StudentPlacements();
        studentPlacements.setPlacements(placements.get());
        studentPlacements.setStudentResult(students);
        return studentPlacements;
    }

}
