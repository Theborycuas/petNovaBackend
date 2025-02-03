package com.codesoftlution.petNova.user_microservice.dtos;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;  // Nombre de usuario
    private String password;  // Contraseña
}