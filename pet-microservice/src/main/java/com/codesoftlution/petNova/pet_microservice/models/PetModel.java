package com.codesoftlution.petNova.pet_microservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "pets")
public class PetModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String race;
    private Integer age;
    private String color;
    private boolean active;

    @Column(columnDefinition = "TEXT")
    private String obsevations;

    @ManyToOne
    @JoinColumn(name = "species_id", nullable = false)
    private SpecieModel specie;

    @Column(nullable = false)
    private Long userId;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime deletedAt;
    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
    public boolean isDeleted() {
        return this.deletedAt != null;
    }

}
