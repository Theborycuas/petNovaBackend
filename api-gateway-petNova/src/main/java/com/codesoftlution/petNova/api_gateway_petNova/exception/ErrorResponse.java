package com.codesoftlution.petNova.api_gateway_petNova.exception;

import lombok.Data;

import java.time.Instant;

@Data
public class ErrorResponse {
    private int status;
    private String message;
    private Instant timestamp;

    // Constructor con 3 parámetros
    public ErrorResponse(int status, String message, Instant timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
}
