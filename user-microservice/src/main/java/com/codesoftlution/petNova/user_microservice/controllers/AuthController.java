package com.codesoftlution.petNova.user_microservice.controllers;

import com.codesoftlution.petNova.user_microservice.dtos.AuthRequest;
import com.codesoftlution.petNova.user_microservice.dtos.AuthResponse;
import com.codesoftlution.petNova.user_microservice.dtos.RegisterRequest;
import com.codesoftlution.petNova.user_microservice.response.AuthSesionResponse;
import com.codesoftlution.petNova.user_microservice.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("apiPetNova/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/userRegister")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/userLogin")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/validateUserTokenActive")
    public ResponseEntity<?> validateUserTokenActive(@RequestHeader("Authorization") String token) {
        try {
            boolean isTokenIsValid = authService.validateUserTokenActive(token.replace("Bearer ", ""));
            if (!isTokenIsValid) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new AuthSesionResponse(false, "TOKEN INVALIDO O EXPIRADO"));
            }
            return ResponseEntity.ok(new AuthSesionResponse(true, "TOKEN VALIDADO"));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthSesionResponse(false, "ERROR AL VALIDAR EL TOKEN " + e.getMessage()));
        }
    }
}
