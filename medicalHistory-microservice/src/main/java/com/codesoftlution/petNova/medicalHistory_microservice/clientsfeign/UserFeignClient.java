package com.codesoftlution.petNova.medicalHistory_microservice.clientsfeign;

import com.codesoftlution.petNova.medicalHistory_microservice.dtos.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-microservice", url = "http://localhost:8001", path = "/apiPetNova")
public interface UserFeignClient {

    @GetMapping("users/getUserById/{userId}")
    UserDTO getUserById(
            @RequestHeader("Authorization") String token,
            @PathVariable("userId") Long userId);
}
