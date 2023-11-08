package com.distributedsystems.resultmanagement.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.distributedsystems.resultmanagement.entity.Results;
import com.distributedsystems.resultmanagement.entity.StudentResult;
import com.distributedsystems.resultmanagement.entity.StudentResultCgpa;
import com.distributedsystems.resultmanagement.repository.ResultRepository;
import com.distributedsystems.resultmanagement.service.ResultService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ResultServiceImplementation implements ResultService{
    
    @Autowired
    ResultRepository resultRepo;
    
    private final WebClient webClient;

    public ResultServiceImplementation(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }
    @Override
    public void addResults(Results result) {
        log.info("results added");
        resultRepo.save(result);
    }

    @Override
    public StudentResult getResult(Long universityRollNumber) {
        Optional<Results> existingResult = resultRepo.getResultByRollNumber(universityRollNumber);
        StudentResult studentResult = webClient.get()
                .uri("http://localhost:6061/"+universityRollNumber)
                .retrieve()
                .bodyToMono(StudentResult.class)
                .block();
        log.info("get result by roll number");
        studentResult.setResults(existingResult.get());
        if(existingResult.isEmpty()) {
            throw new NoSuchElementException("University RollNumber does not exists");
        }        
        return studentResult;
    }

    @Override
    public List<StudentResultCgpa> getResults(double cgpa) {
        List<Results> results = resultRepo.getResultAboveCgpa(cgpa);
        List<StudentResultCgpa> studentResult = webClient.get()
                .uri("http://localhost:6061/")
                .retrieve()
                .bodyToFlux(StudentResultCgpa.class)
                .collectList()
                .block();
        log.info("get result less than cgpa");
        List<StudentResultCgpa> studentResultCgpa = new ArrayList<>();
        for (StudentResultCgpa student : studentResult) {
            for (Results result : results) {
                if (student.getUniversityRollNumber().equals(result.getUniversityRollNumber())) {
                    student.setCgpa(result.getCgpa());
                    studentResultCgpa.add(student);
                    break;
                }
            }
        }
        return studentResultCgpa;
    }
}
