package com.cinego.cingobackend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Le nom d'utilisateur est obligatoire.")
    @Size(min = 3, max = 50, message = "Le nom d'utilisateur doit contenir entre 3 et 50 caractères.")
    private String username;

    @NotBlank(message = "L'adresse email est obligatoire.")
    @Email(message = "Veuillez fournir une adresse email valide.")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire.")
    @Size(min = 4, max = 100, message = "Le mot de passe doit contenir au moins 4 caractères.")
    private String password;
}
