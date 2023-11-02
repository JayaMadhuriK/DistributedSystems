package com.distributedsystems.studentmanagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.distributedsystems.studentmanagement.entity.Student;
import com.distributedsystems.studentmanagement.service.StudentService;

@RestController
public class StudentController {
    @Autowired
    StudentService studentService;
    
    @PostMapping("/add")
    public void addStudents(@RequestBody Student student) {
        studentService.addStudents(student);
    }
    
    @GetMapping("/")
    public List<Student> getStudents() {
        return studentService.getStudents();
    }
    
    @GetMapping("/{rollNumber}")
    public Optional<Student> getStudentByRollNumber(@PathVariable Long rollNumber) {
        return studentService.getStudentByRollNumber(rollNumber);
    }
}
