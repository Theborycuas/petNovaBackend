package com.codesoftlution.petNova.user_microservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class UserOfficeKey implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "office_id")
    private Long officeId;

    public UserOfficeKey() {}

    public UserOfficeKey(Long userId, Long officeId) {
        this.userId = userId;
        this.officeId = officeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserOfficeKey)) return false;
        UserOfficeKey that = (UserOfficeKey) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(officeId, that.officeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, officeId);
    }
}

