package com.distributedsystems.resultmanagement.service;

import java.util.List;

import com.distributedsystems.resultmanagement.entity.Results;
import com.distributedsystems.resultmanagement.entity.StudentResult;
import com.distributedsystems.resultmanagement.entity.StudentResultCgpa;

public interface ResultService {
    void addResults(Results result);
    StudentResult getResult(Long universityRollNumber);
    List<StudentResultCgpa> getResults(double cgpa);
}
