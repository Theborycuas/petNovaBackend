package com.codesoftlution.petNova.user_microservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserModel {

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

/*    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + role.getRoleName());
    }*/
}
