package com.distributedsystems.resultmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.distributedsystems.resultmanagement.entity.Results;

@Repository
public interface ResultRepository extends JpaRepository<Results, Long>{
    @Query("select s from Results as s where s.universityRollNumber = :universityRollNumber")
    Optional<Results> getResultByRollNumber(Long universityRollNumber);
    @Query("select s from Results as s where s.cgpa > :cgpa")
    List<Results> getResultAboveCgpa(double cgpa);
}
