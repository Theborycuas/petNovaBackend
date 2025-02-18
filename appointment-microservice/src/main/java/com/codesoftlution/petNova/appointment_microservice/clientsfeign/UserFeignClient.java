package com.codesoftlution.petNova.appointment_microservice.clientsfeign;


import com.codesoftlution.petNova.appointment_microservice.dtos.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-microservice", path = "/apiPetNova")
public interface UserFeignClient {

    @GetMapping("/users/getUserById/{userId}")
    UserDTO getUserById(
            @RequestHeader("Authorization") String token,
            @PathVariable("userId") Long userId);
}
