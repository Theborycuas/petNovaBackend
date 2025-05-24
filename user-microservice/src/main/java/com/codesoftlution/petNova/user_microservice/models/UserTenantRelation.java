package com.codesoftlution.petNova.user_microservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "user_tenant")
public class UserTenantRelation {

    @EmbeddedId
    private UserTenantKey id;

    @Column(name = "assigned_at", nullable = false)
    private LocalDateTime assignedAt = LocalDateTime.now();

    @Column(name = "status", nullable = false)
    private String status = "ACTIVE"; // ACTIVO, INACTIVO, PENDIENTE, etc.

    @Column(name = "role", nullable = false)
    private String role; // Ej: SUPER_ADMIN, TENANT_ADMIN, VETERINARIO, etc.

    public UserTenantRelation() {}

    public UserTenantRelation(UserTenantKey id, String role, String status) {
        this.id = id;
        this.role = role;
        this.status = status;
        this.assignedAt = LocalDateTime.now();
    }
}
