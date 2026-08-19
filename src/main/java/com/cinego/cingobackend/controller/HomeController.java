package com.cinego.cingobackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@CrossOrigin("http://localhost:4200")
@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> rootInfo() {
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("application", "CineGo Cinema Management API");
        info.put("status", "UP & RUNNING");
        info.put("frontendUrl", "http://localhost:4200");
        info.put("filmsApi", "http://localhost:8080/api/films");
        info.put("actuatorHealth", "http://localhost:8080/actuator/health");
        info.put("message", "Bienvenue sur l'API CineGo !");
        return ResponseEntity.ok(info);
    }
}
