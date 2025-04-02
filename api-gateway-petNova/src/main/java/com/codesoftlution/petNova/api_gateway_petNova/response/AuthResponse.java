package com.codesoftlution.petNova.api_gateway_petNova.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthResponse(
        String token,
        String username,
        String role,
        String message
) {
    // Constructor para éxito
    public AuthResponse(String token, String username, String role) {
        this(token, username, role, null);
    }

    // Constructor para errores
    public AuthResponse(String message) {
        this(null, null, null, message);
    }
}
