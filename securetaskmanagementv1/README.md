# Secure Task Management – Authentication Module

## Introduction
This project demonstrates a **database-backed authentication system using Spring Boot and Spring Security**.  
Instead of in-memory users, credentials are stored in a PostgreSQL database and loaded dynamically during login.

The implementation follows **industry-standard Spring Security practices** and is designed to be:
- Beginner-friendly
- Cleanly structured
- Easy to extend with authorization and role-based access

---

## Features
- Database-backed authentication (PostgreSQL)
- Custom `UserDetailsService`
- Custom `UserDetails` (`UserPrincipal`)
- Spring Security filter chain configuration
- Supports:
  - Form-based login
  - HTTP Basic authentication
- Clean separation of concerns (Entity, Repo, Service, Config)

---

## Security Flow Diagram (Textual)

```
Client Request
   ↓
Spring Security Filter Chain
   ↓
AuthenticationManager
   ↓
DaoAuthenticationProvider
   ↓
UserDetailsService (MyUserDetailsService)
   ↓
UserRepo → Database (users table)
   ↓
UserPrincipal (UserDetails)
   ↓
Password Validation
   ↓
Authentication Success
   ↓
SecurityContext populated
```

---

## Code Explanation (Module-wise)

### 1. Users Entity
Represents a user record stored in the database.

```java
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
}
```

**Why this matters:**
- Keeps authentication data centralized
- Uses auto-generated primary keys
- Stores hashed passwords (recommended)

---

### 2. UserPrincipal
Acts as an adapter between the `Users` entity and Spring Security.

```java
public class UserPrincipal implements UserDetails {

    private Users user;

    public UserPrincipal(Users user) {
        this.user = user;
    }

    public Long getUserId() {
        return user.getId();
    }
}
```

**Responsibilities:**
- Supplies username & password to Spring Security
- Exposes user identity for future authorization checks

---

### 3. User Repository
Used to fetch users from the database.

```java
@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
}
```

---

### 4. UserDetailsService
Loads user details during authentication.

```java
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Users user = repo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(user);
    }
}
```

---

### 5. Security Configuration

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(req -> req.anyRequest().authenticated())
            .formLogin(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults())
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}
```

---

## How to Integrate Into Another Project

1. Create `Users` entity
2. Create `UserRepo`
3. Implement `UserDetailsService`
4. Implement `UserPrincipal`
5. Configure:
   - `SecurityFilterChain`
   - `AuthenticationProvider`
   - `PasswordEncoder`
6. Store **BCrypt-hashed passwords** in the database

---

## Common Mistakes
- Storing passwords in plain text
- Using usernames instead of user IDs for authorization
- Mixing stateless and session-based authentication
- Putting authorization logic in controllers

---

## Best Practices
- Always hash passwords using BCrypt
- Use user IDs for ownership checks
- Perform authorization in the service layer
- Keep entities separate from security logic
- Add roles early for scalability

---

## Future Improvements
- Role-based authorization (ADMIN / USER)
- Task ownership enforcement
- JWT-based stateless authentication
- Method-level security with `@PreAuthorize`

---

## Author
Secure authentication module implemented and documented with a focus on **clean architecture and security best practices**.
