package com.codesoftlution.petNova.office_microservice.clientsfeign;

import com.codesoftlution.petNova.office_microservice.dtos.UserDTO;
import com.codesoftlution.petNova.office_microservice.response.AuthSesionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-microservice", url = "http://localhost:8001", path = "/apiPetNova")
public interface UserFeignClient {

    @GetMapping("/users/getUserById/{userId}")
    UserDTO getUserById(@PathVariable("userId") Long userId);

    @GetMapping("/auth/validateUserTokenActive")
    ResponseEntity<AuthSesionResponse> validateTokenActive(@RequestHeader("Authorization") String token);

}
