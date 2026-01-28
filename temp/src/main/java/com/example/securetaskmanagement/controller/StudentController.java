package com.example.securetaskmanagement.controller;

import com.example.securetaskmanagement.model.Student;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
    
    private List<Student> Students = new ArrayList<>(List.of(
        new Student(1L, "Alice", 85),
        new Student(2L, "Bob", 90),
        new Student(3L, "Charlie", 78)

    ));
    @GetMapping("/student")
    public List<Student> getAllStudents() {
        return Students;
    }
    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");   //getAttribute gives object but we want to return CsrfToken, so converting object. 
        //we are casting object to CsrfToken 
    }

    @PostMapping("/student")
    public Student addStudent(@RequestBody Student student) {
        Students.add(student);
        System.out.println("Student " + student + " added successfully.");
        return student;
    }

}
