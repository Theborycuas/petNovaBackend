package com.codesoftlution.petNova.user_microservice.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}