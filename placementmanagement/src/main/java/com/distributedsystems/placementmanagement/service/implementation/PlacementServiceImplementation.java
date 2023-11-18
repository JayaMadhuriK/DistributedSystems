package com.distributedsystems.placementmanagement.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.distributedsystems.placementmanagement.entity.Placements;
import com.distributedsystems.placementmanagement.entity.Results;
import com.distributedsystems.placementmanagement.entity.Student;
import com.distributedsystems.placementmanagement.entity.StudentPlacements;
import com.distributedsystems.placementmanagement.exception.AlreadyExistsException;
import com.distributedsystems.placementmanagement.exception.EmptyResultException;
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
        Optional<Placements> placement = placementRepo.findByCompanyName(placements.getCompanyName());
        if(placement.isPresent()) {
            throw new AlreadyExistsException("Company already registered");
        }
        log.info("add placementdetails");
        placementRepo.save(placements);
    }

    @Override
    public StudentPlacements getPlacementDetails(String companyName) {
        Optional<Placements> placements = placementRepo.findByCompanyName(companyName);
        if(placements.isEmpty()) {
            throw new EmptyResultException("Cannot find Company with name "+companyName);
        }
        log.info("get placementdetails");
        List<Results> results;
        try {
             results = webClient
                    .get()
                    .uri("http://localhost:6062/result/"+placements.get().getMinCgpa())
                    .retrieve()
                    .bodyToFlux(Results.class)
                    .collectList()
                    .block();
        }
        catch(WebClientResponseException ex) {
            throw new EmptyResultException("No data received from Result management");
        }
        catch(WebClientRequestException ex) {
            throw new EmptyResultException("No data received from Result management");
        }
        List<Student> students;
        try {
            students = webClient
                    .get()
                    .uri("http://localhost:6061/")
                    .retrieve()
                    .bodyToFlux(Student.class)
                    .collectList()
                    .block();
        }
        catch(WebClientResponseException ex) {
            throw new EmptyResultException("No data received from Student management");
        }
        catch(WebClientRequestException ex) {
            throw new EmptyResultException("No data received from Student management");
        }
        List<Student> studentResultCgpa = new ArrayList<>();
        for (Student student : students) {
            for (Results result : results) {
                if (student.getUniversityRollNumber().equals(result.getUniversityRollNumber())) {
                    student.setCgpa(result.getCgpa());
                    studentResultCgpa.add(student);
                    break;
                }
            }
        }
        StudentPlacements studentPlacements = new StudentPlacements();
        studentPlacements.setPlacements(placements.get());
        studentPlacements.setStudentResult(students);
        return studentPlacements;
    }

}
