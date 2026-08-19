package com.cinego.cingobackend.dto;

import lombok.Data;

@Data
public class ProfileForm {

    private String username;
    private String email;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}