package com.distributedsystems.studentmanagement.service.implementation;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.distributedsystems.studentmanagement.entity.Student;
import com.distributedsystems.studentmanagement.exception.AlreadyExistsException;
import com.distributedsystems.studentmanagement.repository.StudentRepository;
import com.distributedsystems.studentmanagement.service.StudentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentServiceImplementation implements StudentService{
    
    @Autowired
    StudentRepository studentRepo;
    
    @Override
    public void addStudents(Student student) {
        Optional<Student> existingStudent = studentRepo.getStudentByRollNumber(student.getUniversityRollNumber());
        if(existingStudent.isPresent()) {
            throw new AlreadyExistsException("Student with roll number already exists");
        }
        log.info("student added");
        studentRepo.save(student);
    }

    @Override
    public List<Student> getStudents() {
        log.info("get all students");
        List<Student> listOfStudents = studentRepo.findAll();
        return listOfStudents;
    }

    @Override
    public Optional<Student> getStudentByRollNumber(Long studentRollNumber) {
        log.info("get students by roll number");
        Optional<Student> student = studentRepo.getStudentByRollNumber(studentRollNumber);
        if(student.isEmpty()) {
            throw new NoSuchElementException("RollNumber does not exists");
        }
        return student;
    }

}
