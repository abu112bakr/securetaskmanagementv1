package com.example.securetaskmanagement.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;



@RestController
public class HomeController {

    @GetMapping("/")
    public String greet(HttpServletRequest request){
        return "Welcome to Secure web application "+ request.getSession().getId();
    }
}
// video 30 spring security | basic
//application.properties to make custom username and password
// # when spring-boot-starter-security dependency is enabled in pom.xml
// # default spring boot username is: user 
// # defult spring boot password is given in console like this: b3376005-6e14-44b1-aa95-a8373ec077da
// #Custom password to be used in default spring security
// spring.security.user.name=user
// spring.security.user.password=cod

//video 31 spring security | CSRF Token 
// @PostMapping("/student")
// public Student addStudent(@RequestBody Student student){
//     students.add(student);
//     return student;
// }
//without token you cant post this
//in Student Controller
// @GetMapping("/csrf-token")
// public CsrfToken getCsrfToken(HttpServletRequest request){ 
//     return (CsrfToken) request.getAttribute("_csrf");
// }
//Console will give
// "token": "b1fSc3sGddjKp7xHpfKn6LkahehImBk_FeSt-TxiKhKjRvteCzLlER1iFO7nw9kkkd-T34woqNB7oS0Sd4LJnAlbHnCQJ508",
// "parameterName": "_csrf",
// "headerName": "X-CSRF-TOKEN"
//use in postman header data
// key: X-CSRF-TOKEN
// value: b1fSc3sGddjKp7xHpfKn6LkahehImBk_FeSt-TxiKhKjRvteCzLlER1iFO7nw9kkkd-T34woqNB7oS0Sd4LJnAlbHnCQJ508

//video 32 spring security | Custom security filter chain without CSF token
//CREATED config package, SecurityConfig.java
//@Configuration
// @EnableWebSecurity // follow my custom custom security fitler chain
// public class SecurityConfig {
//     @Bean
//     // http.csrf(customizer -> customizer.disable()); disable csrf token
//     //http.authorizeHttpRequests(request -> request.anyRequest().authenticated()) every request must be authenticated
//     //http.formLogin(Customizer.withDefaults()); // default login form
//     //http.httpBasic(Customizer.withDefaults()); // accept login from postman
//     //.formLogin(Customizer.withDefaults())
//     public SecurityFilterChain securityFilterChanin(HttpSecurity http) throws Exception {
//         //these are   labmda expression
//         return http
//                 .csrf(customizer -> customizer.disable())
//                 .authorizeHttpRequests(request -> request.anyRequest().authenticated())
//                 .formLogin(Customizer.withDefaults())
//                 .httpBasic(Customizer.withDefaults())
//                 .sessionManagement(session -> 
//                     session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                 .build();
//     }

// video 33 Spring security | verify user from database || Added database || spring boot v3.5.10
// objective is to verify user from database
// Part 1
// we verify user details from database using UserDetailsService interface, we will customize it
// we will use InMemoryUserDetailsManager which is a class that implements UserDetailsService interface
// InMemoryUserDetailsManager need object of UserDetails, UserDetails is an interface so we need a class for it. 
// we use inbuild User from spring framwork & this User is a class which implements UserDetails interface
// we will use builder of User class





