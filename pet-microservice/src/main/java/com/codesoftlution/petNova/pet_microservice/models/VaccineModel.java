package com.codesoftlution.petNova.pet_microservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "vaccines")
public class VaccineModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vaccineName;
    private LocalDateTime aplicationDate;
    private LocalDateTime nextAplicationDate;
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "mascota_id", nullable = false)
    private PetModel petModel;

    private Long veterinarianId;

    private Long medicalHId;
}
