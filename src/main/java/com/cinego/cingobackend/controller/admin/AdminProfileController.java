package com.cinego.cingobackend.controller.admin;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cinego.cingobackend.dto.ProfileForm;
import com.cinego.cingobackend.entity.User;
import com.cinego.cingobackend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/profile")
@RequiredArgsConstructor
public class AdminProfileController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String profile(Authentication authentication, Model model) {
        User user = currentUser(authentication);
        ProfileForm form = new ProfileForm();
        form.setUsername(user.getUsername());
        form.setEmail(user.getEmail());
        model.addAttribute("form", form);
        model.addAttribute("error", null);
        return "admin/profile";
    }

    @PostMapping
    public String update(Authentication authentication, @ModelAttribute ProfileForm form, Model model) {
        User user = currentUser(authentication);
        String error = validate(form, user);

        if (error != null) {
            model.addAttribute("form", form);
            model.addAttribute("error", error);
            return "admin/profile";
        }

        boolean changePassword = form.getNewPassword() != null && !form.getNewPassword().isEmpty();
        if (changePassword) {
            user.setPassword(passwordEncoder.encode(form.getNewPassword()));
        }
        user.setUsername(form.getUsername());
        user.setEmail(form.getEmail());
        userRepository.save(user);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        UsernamePasswordAuthenticationToken newAuth =
                new UsernamePasswordAuthenticationToken(form.getUsername(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return "redirect:/admin/profile?updated";
    }

    private String validate(ProfileForm form, User current) {
        if (form.getUsername() == null || form.getUsername().isBlank()) {
            return "Le username est obligatoire.";
        }
        if (form.getEmail() == null || form.getEmail().isBlank()) {
            return "L'email est obligatoire.";
        }
        if (userRepository.findByUsername(form.getUsername())
                .map(u -> !u.getId().equals(current.getId())).orElse(false)) {
            return "Ce username est deja utilise.";
        }
        if (userRepository.findByEmail(form.getEmail())
                .map(u -> !u.getId().equals(current.getId())).orElse(false)) {
            return "Cet email est deja utilise.";
        }
        if (form.getCurrentPassword() == null || form.getCurrentPassword().isEmpty()) {
            return "Saisissez votre mot de passe actuel.";
        }
        if (!passwordEncoder.matches(form.getCurrentPassword(), current.getPassword())) {
            return "Mot de passe actuel incorrect.";
        }
        boolean newPasswordProvided = form.getNewPassword() != null && !form.getNewPassword().isEmpty();
        if (newPasswordProvided && !form.getNewPassword().equals(form.getConfirmPassword())) {
            return "La confirmation du nouveau mot de passe ne correspond pas.";
        }
        return null;
    }

    private User currentUser(Authentication authentication) {
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElseThrow();
    }
}