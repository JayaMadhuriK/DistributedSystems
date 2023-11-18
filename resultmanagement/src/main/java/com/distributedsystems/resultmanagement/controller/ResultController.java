package com.distributedsystems.resultmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.distributedsystems.resultmanagement.entity.Results;
import com.distributedsystems.resultmanagement.entity.StudentResult;
import com.distributedsystems.resultmanagement.service.ResultService;

@RestController
public class ResultController {
    @Autowired
    ResultService resultService;
    
    @PostMapping("/add")
    public void addResult(@RequestBody Results result) {
        resultService.addResults(result);
    }
    
    @GetMapping("/{rollNumber}")
    public StudentResult getResult(@PathVariable Long rollNumber) {
        return resultService.getResult(rollNumber);
    }
    
    @GetMapping("/result/{cgpa}")
    public List<Results> getResults(@PathVariable double cgpa) {
        return resultService.getResults(cgpa);
    }
}
