package com.codesoftlution.petNova.user_microservice.controllers;

import com.codesoftlution.petNova.user_microservice.dtos.AuthRequest;
import com.codesoftlution.petNova.user_microservice.dtos.AuthResponse;
import com.codesoftlution.petNova.user_microservice.dtos.RegisterRequest;
import com.codesoftlution.petNova.user_microservice.response.AuthSesionResponse;
import com.codesoftlution.petNova.user_microservice.services.AuthService;
import com.codesoftlution.petNova.user_microservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

import static com.codesoftlution.petNova.user_microservice.utils.Constants.*;

@RestController
@RequestMapping("apiPetNova/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final Logger log = Logger.getLogger(AuthController.class.getName());

    @PostMapping("/userRegister")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        log.info("START USER REGISTER");
        AuthResponse authResponse = authService.register(request);
        log.info("END USER REGISTER");
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/userLogin")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        log.info("START USER LOGIN");
        AuthResponse authResponse = authService.authenticate(request);
        log.info("END USER LOGIN");
        return ResponseEntity.ok()
                .body(authResponse);
    }

    @GetMapping("/validateUserTokenActive")
    public ResponseEntity<?> validateUserTokenActive(@RequestHeader("Authorization") String token) {
        try {
            log.info("START VALIDATE USER TOKEN ACTIVE");
            boolean isTokenIsValid = authService.validateUserTokenActive(token.replace(PREFIX_BEARER, ""));
            if (!isTokenIsValid) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new AuthSesionResponse(false, "TOKEN INVALIDO O EXPIRADO"));
            }
            log.info("END VALIDATE USER TOKEN ACTIVE");
            return ResponseEntity.ok(new AuthSesionResponse(true, "TOKEN VALIDADO"));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthSesionResponse(false, "ERROR AL VALIDAR EL TOKEN " + e.getMessage()));
        }
    }
}
