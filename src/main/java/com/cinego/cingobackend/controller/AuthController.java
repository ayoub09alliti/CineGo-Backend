package com.cinego.cingobackend.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinego.cingobackend.dto.auth.AuthResponse;
import com.cinego.cingobackend.dto.auth.LoginRequest;
import com.cinego.cingobackend.dto.auth.RegisterRequest;
import com.cinego.cingobackend.model.Customers;
import com.cinego.cingobackend.entity.User;
import com.cinego.cingobackend.repository.CustomersRepository;
import com.cinego.cingobackend.repository.UserRepository;
import com.cinego.cingobackend.security.JwtService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final CustomersRepository customersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        String identifier = request.getUsername().trim();
        String rawPassword = request.getPassword();

        // 1. Try Customer authentication (Table 'customers' for frontend clients)
        Customers customer = customersRepository.findByEmail(identifier)
                .or(() -> customersRepository.findByFirstname(identifier))
                .orElse(null);

        if (customer != null && customer.getPassword() != null && passwordEncoder.matches(rawPassword, customer.getPassword())) {
            String token = jwtService.generateToken(customer);
            AuthResponse response = AuthResponse.builder()
                    .token(token)
                    .id(customer.getId())
                    .username(customer.getFirstname())
                    .email(customer.getEmail())
                    .role("ROLE_USER")
                    .build();
            return ResponseEntity.ok(response);
        }

        // 2. Fallback to User table ('t_user' for admin)
        User user = userRepository.findByUsername(identifier)
                .or(() -> userRepository.findByEmail(identifier))
                .orElse(null);

        if (user != null && user.getPassword() != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            String token = jwtService.generateToken(user);
            AuthResponse response = AuthResponse.builder()
                    .token(token)
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .build();
            return ResponseEntity.ok(response);
        }

        Map<String, Object> error = new LinkedHashMap<>();
        error.put("status", HttpStatus.UNAUTHORIZED.value());
        error.put("error", "Unauthorized");
        error.put("message", "Nom d'utilisateur/Email ou mot de passe incorrect.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        String username = request.getUsername().trim();
        String email = request.getEmail().trim().toLowerCase();

        if (customersRepository.existsByEmail(email) || userRepository.existsByEmail(email)) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("status", HttpStatus.CONFLICT.value());
            error.put("error", "Conflict");
            error.put("message", "Cette adresse email est déjà enregistrée.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        Customers newCustomer = new Customers();
        newCustomer.setFirstname(username);
        newCustomer.setLastname("Client");
        newCustomer.setEmail(email);
        newCustomer.setPassword(passwordEncoder.encode(request.getPassword()));

        Customers savedCustomer = customersRepository.save(newCustomer);

        String token = jwtService.generateToken(savedCustomer);

        AuthResponse response = AuthResponse.builder()
                .token(token)
                .id(savedCustomer.getId())
                .username(savedCustomer.getFirstname())
                .email(savedCustomer.getEmail())
                .role("ROLE_USER")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Non authentifié."));
        }

        String identifier = authentication.getName();

        Customers customer = customersRepository.findByEmail(identifier)
                .or(() -> customersRepository.findByFirstname(identifier))
                .orElse(null);

        if (customer != null) {
            AuthResponse response = AuthResponse.builder()
                    .id(customer.getId())
                    .username(customer.getFirstname())
                    .email(customer.getEmail())
                    .role("ROLE_USER")
                    .build();
            return ResponseEntity.ok(response);
        }

        User user = userRepository.findByUsername(identifier)
                .or(() -> userRepository.findByEmail(identifier))
                .orElse(null);

        if (user != null) {
            AuthResponse response = AuthResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .build();
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Utilisateur introuvable."));
    }
}
