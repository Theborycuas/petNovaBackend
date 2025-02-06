package com.codesoftlution.petNova.office_microservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "offices")
public class OfficeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(columnDefinition = "TEXT")
    private String linkLogoPhoto;

    private boolean active;

    //Veterinario Asociado al consultorio
    private Long veterinarioId;
}
