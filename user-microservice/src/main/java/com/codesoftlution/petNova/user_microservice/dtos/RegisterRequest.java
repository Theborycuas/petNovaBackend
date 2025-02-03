package com.codesoftlution.petNova.user_microservice.dtos;

import com.codesoftlution.petNova.user_microservice.models.RoleModel;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterRequest {

    private RoleModel role;        // Rol del usuario (OWNER, VETERINARIAN, ADMIN, etc.)

    private String name;
    private String idNumber;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private boolean active;
    private LocalDateTime creationDate;
    private String linkPerfilPhoto;

    private Long officeId;
}