package com.distributedsystems.studentmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.distributedsystems.studentmanagement.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
    @Query("select s from Student as s where s.universityRollNumber = :universityRollNumber")
    Optional<Student> getStudentByRollNumber(Long universityRollNumber);
}
