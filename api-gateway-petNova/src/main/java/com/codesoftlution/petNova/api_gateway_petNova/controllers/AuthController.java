package com.codesoftlution.petNova.api_gateway_petNova.controllers;

import com.codesoftlution.petNova.api_gateway_petNova.clientsfeign.UserFeignClient;
import com.codesoftlution.petNova.api_gateway_petNova.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/apiPetNova/auth")
public class AuthController {

    private final UserFeignClient userFeignClient;
    private final ReactiveUserDetailsService userDetailsService;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserFeignClient userFeignClient,
                          ReactiveUserDetailsService userDetailsService,
                          JwtTokenProvider tokenProvider,
                          PasswordEncoder passwordEncoder) {
        this.userFeignClient = userFeignClient;
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/userRegister")
    public Mono<ResponseEntity<UserFeignClient.UserResponse>> register(@RequestBody @Valid RegisterRequest request) {
        return userFeignClient.createUser(
                new UserFeignClient.UserRequest(
                        request.name(),
                        request.idNumber(),
                        request.username(),
                        request.email(),
                        request.password(),
                        "USUARIO",
                        request.phoneNumber(),
                        "https://test.com"
                )
        ).map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @PostMapping("/userLogin")
    public Mono<AuthResponse> login(@RequestBody AuthRequest request) {
        return userDetailsService.findByUsername(request.username())
                .filter(userDetails -> passwordEncoder.matches(request.password(), userDetails.getPassword()))
                .flatMap(userDetails -> generateTokenResponse(userDetails))
                .switchIfEmpty(Mono.error(new BadCredentialsException("Credenciales inválidas")));
    }

    private Mono<AuthResponse> generateTokenResponse(UserDetails userDetails) {
        return Mono.just(new AuthResponse(
                tokenProvider.generateToken(
                        userDetails.getUsername(),
                        userDetails.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.joining(","))
                )));
    }

    // Records para DTOs
    public record AuthRequest(String username, String password) {}
    public record AuthResponse(String token) {}
    public record RegisterRequest(String name, String idNumber, String username, String email, String password,  String rollName, String phoneNumber, String linkPerfilPhoto) {}
}
