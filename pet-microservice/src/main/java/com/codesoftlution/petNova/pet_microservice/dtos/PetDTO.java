package com.codesoftlution.petNova.pet_microservice.dtos;

import com.codesoftlution.petNova.pet_microservice.models.SpecieModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetDTO {
    private Long id;
    private String name;
    private String race;
    private Integer age;
    private String color;
    private boolean active;
    private String obsevations;
    private String specieName;
    private Long userId;
}
