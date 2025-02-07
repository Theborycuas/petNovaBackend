package com.codesoftlution.petNova.appointment_microservice.clientsfeign;

import com.codesoftlution.petNova.appointment_microservice.dtos.PetDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "pet-microservice", url = "http://localhost:8002", path = "/apiPetNova")
public interface PetFeignClient {

    @GetMapping("/pets/getPetById/{petId}")
    PetDTO getPetById(
            @RequestHeader("Authorization") String token,
            @PathVariable("petId") Long petId);
}
