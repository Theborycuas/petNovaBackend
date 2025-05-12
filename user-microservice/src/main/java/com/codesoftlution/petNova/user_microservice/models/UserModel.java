package com.codesoftlution.petNova.user_microservice.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserModel implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String idNumber;

    @Column(unique = true, nullable = false)
    private String username;

    private String address;
    private String city;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    private String passwordResetToken;
    private LocalDateTime passwordResetTokenExpiry;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private RoleModel role;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private boolean active = false;

    private String gender;

    @Column(nullable = false)
    private boolean emailVerified = false;
    private LocalDateTime emailVerifiedAt;

    @Column(columnDefinition = "TEXT")
    private String firebaseToken;

    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    @Column(columnDefinition = "TEXT")
    private String avatarUrl;

    private Long officeId;

    private Long tenantId;

    private String preferredLanguage;
    private String timeZone;
    private LocalDateTime lastLoginAt;
    private boolean notificationsEnabled = true;

    private LocalDateTime deletedAt;
    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
    public boolean isDeleted() {
        return this.deletedAt != null;
    }

    // Implementación de UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Puedes personalizar esto si necesitas lógica de expiración
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Puedes personalizar esto
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Puedes personalizar esto
    }

    @Override
    public boolean isEnabled() {
        return active; // Usa el campo 'active' para determinar si el usuario está habilitado
    }
}
