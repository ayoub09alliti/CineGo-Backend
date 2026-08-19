package com.cinego.cingobackend.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Le nom d'utilisateur ou l'email est obligatoire.")
    private String username;

    @NotBlank(message = "Le mot de passe est obligatoire.")
    private String password;
}
