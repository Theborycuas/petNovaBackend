package com.codesoftlution.petNova.user_microservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_office")
@Data
public class UserOfficeRelation {

    @EmbeddedId
    private UserOfficeKey id;

    @Column(name = "assigned_at", nullable = false)
    private LocalDateTime assignedAt = LocalDateTime.now();

    @Column(name = "status", nullable = false)
    private String status = "ACTIVE"; // o INACTIVE, PENDING, etc.

    @Column(name = "role", nullable = false)
    private String role; // Ej: "Veterinario", "Administrador", etc.

    // Constructores
    public UserOfficeRelation() {}

    public UserOfficeRelation(UserOfficeKey id, String role, String status) {
        this.id = id;
        this.role = role;
        this.status = status;
        this.assignedAt = LocalDateTime.now();
    }
}