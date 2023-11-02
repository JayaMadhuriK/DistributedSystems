package com.distributedsystems.resultmanagement.service.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    
    @Autowired
    RestTemplate restTemplate;
    @Override
    public void addResults(Results result) {
        resultRepo.save(result);
    }

    @Override
    public StudentResult getResult(Long universityRollNumber) {
        RestTemplate restTemplate = new RestTemplate();
        Optional<Results> existingResult = resultRepo.getResultByRollNumber(universityRollNumber);
        StudentResult studentResult = restTemplate.getForObject("http://localhost:6061/"+universityRollNumber, StudentResult.class);
        log.info("get result by roll number");
        studentResult.setResults(existingResult.get());
        if(existingResult.isEmpty()) {
            throw new NoSuchElementException("University RollNumber does not exists");
        }        
        return studentResult;
    }

    @Override
    public List<StudentResultCgpa> getResults(double cgpa) {
        RestTemplate restTemplate = new RestTemplate();
        List<Results> results = resultRepo.getResultAboveCgpa(cgpa);
        StudentResultCgpa[] studentResult = restTemplate.getForObject("http://localhost:6061/", StudentResultCgpa[].class);
        log.info("get result less than cgpa");
        List<StudentResultCgpa> studentResultList = Arrays.asList(studentResult);
        List<StudentResultCgpa> studentResultCgpa = new ArrayList<>();
        for (StudentResultCgpa student : studentResultList) {
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
