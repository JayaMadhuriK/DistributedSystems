package com.distributedsystems.studentmanagement.service;

import java.util.List;
import java.util.Optional;

import com.distributedsystems.studentmanagement.entity.Student;

public interface StudentService {
    void addStudents(Student student);
    List<Student> getStudents();
    Optional<Student> getStudentByRollNumber(Long studentRollNumber);
}
