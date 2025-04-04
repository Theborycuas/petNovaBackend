package com.codesoftlution.petNova.user_microservice.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private RoleModel role;
    private String phoneNumber;

    @Column(nullable = false)
    private boolean active = false;

    @Column(nullable = false)
    private boolean emailVerified = false;

    private boolean borrado;

    @Column(columnDefinition = "TEXT")
    private String firebaseToken;

    private LocalDateTime creationDate;
    private LocalDateTime updateDate;

    @Column(columnDefinition = "TEXT")
    private String linkPerfilPhoto;

    private Long officeId;

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
