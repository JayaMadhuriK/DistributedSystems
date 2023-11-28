package com.distributedsystems.studentmanagement.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.distributedsystems.studentmanagement.entity.Results;
import com.distributedsystems.studentmanagement.entity.Student;
import com.distributedsystems.studentmanagement.exception.AlreadyExistsException;
import com.distributedsystems.studentmanagement.exception.EmptyResultException;
import com.distributedsystems.studentmanagement.repository.StudentRepository;
import com.distributedsystems.studentmanagement.service.StudentService;

import brave.Span;
import brave.Tracer;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class StudentServiceImplementation implements StudentService{
    
    @Autowired
    StudentRepository studentRepo;
    private final Tracer tracer;
    private final WebClient webClient;

    public StudentServiceImplementation(WebClient.Builder webClientBuilder, Tracer tracer) {
        this.webClient = webClientBuilder.build();
        this.tracer = tracer;
    }
    @Override
    public void addStudents(Student student) {
        Span span = tracer.currentSpan();
        Optional<Student> existingStudent = studentRepo.getStudentByRollNumber(student.getUniversityRollNumber());
        if(existingStudent.isPresent()) {
            span.tag("execption-type","AlreadyExistsException");
            throw new AlreadyExistsException("Student with roll number already exists");
        }
        Results result = new Results();
        result.setUniversityRollNumber(student.getUniversityRollNumber());
        try {
            webClient.post()
            .uri("http://localhost:6062/add")
            .body(Mono.just(result),Results.class)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
        }catch(WebClientResponseException ex) {
            span.tag("execption-type","WebClientResponseException");
            throw new RuntimeException("Failed to insert into results");
        }
        catch(WebClientRequestException e) {
            span.tag("execption-type","WebClientRequestException");
            throw new RuntimeException("Failed to insert into results");
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
        Span span = tracer.currentSpan();
        log.info("get students by roll number");
        Optional<Student> student = studentRepo.getStudentByRollNumber(studentRollNumber);
        if(student.isEmpty()) {
            span.tag("execption-type","EmptyResultException");
            throw new EmptyResultException("RollNumber does not exists");
        }
        return student;
    }

}
