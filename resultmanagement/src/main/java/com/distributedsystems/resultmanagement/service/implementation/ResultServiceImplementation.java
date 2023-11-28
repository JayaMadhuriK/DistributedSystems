package com.distributedsystems.resultmanagement.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.distributedsystems.resultmanagement.entity.Results;
import com.distributedsystems.resultmanagement.entity.StudentResult;
import com.distributedsystems.resultmanagement.repository.ResultRepository;
import com.distributedsystems.resultmanagement.service.ResultService;
import com.distributedsystems.studentmanagement.exception.EmptyResultException;

import brave.Span;
import brave.Tracer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ResultServiceImplementation implements ResultService{
    
    @Autowired
    ResultRepository resultRepo;
    private final Tracer tracer;
    private final WebClient webClient;

    public ResultServiceImplementation(WebClient.Builder webClientBuilder, Tracer tracer) {
        this.webClient = webClientBuilder.build();
        this.tracer = tracer;
    }
    @Override
    public void updateResults(Long rollNumber, Results result) {
        Span span = tracer.currentSpan();
        Results existingResult = resultRepo.getResultByRollNumber(rollNumber);
        if(existingResult == null) {
            span.tag("exception-type", "EmptyResultException");
            throw new EmptyResultException("Student with RollNumber "+rollNumber+" doesn't exists");
        }
        existingResult.setMarks(result.getMarks());
        existingResult.setCgpa(result.getCgpa());
        log.info("results added");
        resultRepo.save(existingResult);
    }

    @Override
    public StudentResult getResult(Long universityRollNumber) {
        Span span = tracer.currentSpan();
        Results existingResult = resultRepo.getResultByRollNumber(universityRollNumber);
        if(existingResult == null) {
            span.tag("exception-type", "EmptyResultException");
            throw new EmptyResultException("Student with RollNumber "+universityRollNumber+" doesn't exists");
        }
        StudentResult studentResult = new StudentResult();
        try {
             studentResult = webClient.get()
                    .uri("http://localhost:6061/"+universityRollNumber)
                    .retrieve()
                    .bodyToMono(StudentResult.class)
                    .block();
            log.info("get result by roll number");
            studentResult.setMarks(existingResult.getMarks());
            studentResult.setCgpa(existingResult.getCgpa());
        }
        catch(WebClientResponseException ex) {
            span.tag("exception-type", "WebClientResponseException");
            throw new RuntimeException("No data received from Student management");
        }
        catch(WebClientRequestException e) {
            span.tag("exception-type", "WebClientRequestException");
            throw new RuntimeException("No data received from Student management");
        }
        return studentResult;
    }

    @Override
    public List<Results> getResults(double cgpa) {
        List<Results> results = resultRepo.getResultAboveCgpa(cgpa);
        log.info("get all results");
        return results;
    }
    @Override
    public void addResults(Results result) {
        log.info("result added");
        resultRepo.save(result);
    }
}
