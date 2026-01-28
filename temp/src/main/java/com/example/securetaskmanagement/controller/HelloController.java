package com.example.securetaskmanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HelloController {
     
    @GetMapping("/")
    public String greet(HttpServletRequest request) {
        return "Hello, Secure Task Management! session id = " + request.getSession().getId();
    }
}


// CSRF Cross-Site Request Forgery
// A CSRF attack forces an authenticated user to execute unwanted actions on a web application in which they're currently authenticated.
// some website steals your session ID and send server with your session ID