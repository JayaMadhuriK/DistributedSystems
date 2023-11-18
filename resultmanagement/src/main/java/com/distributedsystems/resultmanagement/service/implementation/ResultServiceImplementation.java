package com.distributedsystems.resultmanagement.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import com.distributedsystems.resultmanagement.entity.Results;
import com.distributedsystems.resultmanagement.entity.StudentResult;
import com.distributedsystems.resultmanagement.exception.AlreadyExistsException;
import com.distributedsystems.resultmanagement.repository.ResultRepository;
import com.distributedsystems.resultmanagement.service.ResultService;
import com.distributedsystems.studentmanagement.exception.EmptyResultException;

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
        Optional<Results> existingResult = resultRepo.getResultByRollNumber(result.getUniversityRollNumber());
        if(existingResult.isPresent()) {
            throw new AlreadyExistsException("Already assigned marks to this student");
        }
        log.info("results added");
        resultRepo.save(result);
    }

    @Override
    public StudentResult getResult(Long universityRollNumber) {
        Optional<Results> existingResult = resultRepo.getResultByRollNumber(universityRollNumber);
        if(existingResult.isEmpty()) {
            throw new EmptyResultException("Cannot find student with rollnumber "+universityRollNumber);
        }
        StudentResult studentResult;
        try {
            studentResult= webClient.get()
                    .uri("http://localhost:6061/"+universityRollNumber)
                    .retrieve()
                    .bodyToMono(StudentResult.class)
                    .block();
        }
        catch(WebClientRequestException e) {
            throw new EmptyResultException("No data received from Student management");
        }
        log.info("get result by roll number");
        studentResult.setSubject1(existingResult.get().getSubject1());
        studentResult.setSubject2(existingResult.get().getSubject2());
        studentResult.setSubject3(existingResult.get().getSubject3());
        studentResult.setCgpa(existingResult.get().getCgpa());
        return studentResult;
    }

    @Override
    public List<Results> getResults(double cgpa) {
        List<Results> results = resultRepo.getResultAboveCgpa(cgpa);
        return results;
    }
}
