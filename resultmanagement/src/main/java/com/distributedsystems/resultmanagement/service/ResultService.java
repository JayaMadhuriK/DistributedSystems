package com.distributedsystems.resultmanagement.service;

import java.util.List;

import com.distributedsystems.resultmanagement.entity.Results;
import com.distributedsystems.resultmanagement.entity.StudentResult;

public interface ResultService {
    void updateResults(Long rollNumber, Results result);
    void addResults(Results result);
    StudentResult getResult(Long universityRollNumber);
    List<Results> getResults(double cgpa);
}
