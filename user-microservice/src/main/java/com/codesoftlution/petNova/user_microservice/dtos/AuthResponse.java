package com.codesoftlution.petNova.user_microservice.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String token;  // Token JWT generado
}