package com.codesoftlution.petNova.user_microservice.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String displayName;
    private String email;
    private String idToken;
    private String refreshToken;
    private long expiresIn;
    private boolean registered;
    private String kind;
    private String localId;
}