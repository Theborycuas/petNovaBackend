package com.codesoftlution.petNova.api_gateway_petNova.clientsfeign;

import com.codesoftlution.petNova.api_gateway_petNova.dtos.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@FeignClient(name = "user-microservice", path = "/apiPetNova")
public interface UserFeignClient {

    @GetMapping("/users/findByUsernameAndActive/{username}")
    Mono<UserResponse> getUserByUsername(@PathVariable String username);

    @PostMapping("/users/createUser")
    Mono<UserResponse> createUser(@RequestBody UserRequest userRequest);

    // Records para DTOs
    record UserRequest(String name, String idNumber, String username, String email, String password,  String rollName, String phoneNumber, String linkPerfilPhoto) {}
    record UserResponse(String id, String username, String role) {}
}
