package com.codesoftlution.petNova.office_microservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private String emailContacto;

    private String nombreAdministrador;

    private String identificacionTributaria;

    @Column(updatable = false)
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaUltimaActualizacion;

    // Suscripción actual
    private Long planActual;

    private LocalDate fechaInicioSuscripcion;
    private LocalDate fechaFinSuscripcion;

    // Configuraciones específicas del tenant
    private String zonaHoraria;
    private String idiomaPreferido;
    private String moneda;
    private boolean permiteAgendamientoOnline;
}
