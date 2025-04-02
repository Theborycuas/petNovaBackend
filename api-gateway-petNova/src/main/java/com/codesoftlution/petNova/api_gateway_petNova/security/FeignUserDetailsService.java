package com.codesoftlution.petNova.api_gateway_petNova.security;

import com.codesoftlution.petNova.api_gateway_petNova.clientsfeign.UserFeignClient;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FeignUserDetailsService implements ReactiveUserDetailsService {

    private final UserFeignClient userFeignClient;
    private final PasswordEncoder passwordEncoder;

    public FeignUserDetailsService(UserFeignClient userFeignClient,
                                   PasswordEncoder passwordEncoder) {
        this.userFeignClient = userFeignClient;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userFeignClient.getUserByUsername(username)
                .map(user -> User.builder()
                        .username(user.username())
                        .password(passwordEncoder.encode("[PROTECTED]")) // Asumiendo que el servicio devuelve el password hasheado
                        .roles(user.role())
                        .build()
                );
    }
}