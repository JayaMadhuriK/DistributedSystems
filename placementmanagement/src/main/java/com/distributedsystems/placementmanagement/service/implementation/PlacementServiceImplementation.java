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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlacementServiceImplementation implements PlacementService{

    @Autowired
    PlacementsRepository placementRepo;
    private final WebClient webClient;

    public PlacementServiceImplementation(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }
    @Override
    public void addDetails(Placements placements) {
        log.info("add placementdetails");
        placementRepo.save(placements);
    }

    @Override
    public StudentPlacements getPlacementDetails(Long placementId) {
        Optional<Placements> placements = placementRepo.findById(placementId);
        log.info("get placementdetails");
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
