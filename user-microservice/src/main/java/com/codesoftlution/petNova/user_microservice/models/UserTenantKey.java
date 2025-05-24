package com.codesoftlution.petNova.user_microservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class UserTenantKey implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "tenant_id")
    private Long tenantId;

    public UserTenantKey() {}

    public UserTenantKey(Long userId, Long tenantId) {
        this.userId = userId;
        this.tenantId = tenantId;
    }

    // Getters y setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserTenantKey)) return false;
        UserTenantKey that = (UserTenantKey) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(tenantId, that.tenantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, tenantId);
    }
}
