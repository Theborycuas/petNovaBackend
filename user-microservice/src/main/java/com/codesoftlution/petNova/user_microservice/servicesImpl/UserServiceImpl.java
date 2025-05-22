package com.codesoftlution.petNova.user_microservice.servicesImpl;

import com.codesoftlution.petNova.user_microservice.clientsfeign.OfficeFeignClient;
import com.codesoftlution.petNova.user_microservice.dtos.UserDetailDTO;
import com.codesoftlution.petNova.user_microservice.dtos.UserPublicDTO;
import com.codesoftlution.petNova.user_microservice.interfaces.IUserServices;
import com.codesoftlution.petNova.user_microservice.mappers.UserMapper;
import com.codesoftlution.petNova.user_microservice.models.*;
import com.codesoftlution.petNova.user_microservice.request.RequestUpdateTenantManager;
import com.codesoftlution.petNova.user_microservice.respositories.IRoleRepository;
import com.codesoftlution.petNova.user_microservice.respositories.IUserRepository;
import com.codesoftlution.petNova.user_microservice.respositories.UserOfficeRelationRepository;
import com.codesoftlution.petNova.user_microservice.respositories.UserTenantRelationRepository;
import com.codesoftlution.petNova.user_microservice.utils.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserServices {
    @Autowired
    IUserRepository iUserRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    OfficeFeignClient officeFeignClient;

    @Autowired
    UserOfficeRelationRepository userOfficeRelationRepository;

    @Autowired
    UserTenantRelationRepository userTenantRelationRepository;


    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public UserModel findByUsernameAndActive(String email, boolean active) {
        return iUserRepository.findByUsernameAndActive(email, active)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserDetailDTO getUserDetailById(Long userId) {
        UserModel user = iUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Long> tenantIds = userTenantRelationRepository.findById_UserId(userId)
                .stream()
                .map(rel -> rel.getId().getTenantId())
                .toList();

        List<Long> officeIds = userOfficeRelationRepository.findById_UserId(userId)
                .stream()
                .map(rel -> rel.getId().getOfficeId())
                .toList();

        return UserMapper.toUserDetailDTO(user, tenantIds, officeIds);
    }

    public List<UserDetailDTO> getAllUsers() {
        List<UserModel> userList = iUserRepository.findAllByDeletedAtIsNull();

        return userList.stream()
                .map(user -> {
                    List<Long> tenantIds = userTenantRelationRepository.findById_UserId(user.getId())
                            .stream()
                            .map(rel -> rel.getId().getTenantId())
                            .collect(Collectors.toList());

                    List<Long> officeIds = userOfficeRelationRepository.findById_UserId(user.getId())
                            .stream()
                            .map(rel -> rel.getId().getOfficeId())
                            .collect(Collectors.toList());

                    return UserMapper.toUserDetailDTO(user, tenantIds, officeIds);
                })
                .collect(Collectors.toList());
    }

    public List<UserDetailDTO> getEligibleUsersForAssignment(String context) {
        // Validar que el contexto sea válido
        if (!Set.of("TENANT", "OFFICE").contains(context.toUpperCase())) {
            throw new IllegalArgumentException("Context must be 'TENANT' or 'OFFICE'");
        }

        // 1. Filtrar usuarios activos que no sean ADMIN ni SUPER_ADMIN
        List<UserModel> eligibleUsers = iUserRepository.findAllByDeletedAtIsNull()
                .stream()
                .filter(user -> {
                    String roleName = user.getRole().getRoleName();
                    return !Set.of("ADMIN", "SUPER_ADMIN").contains(roleName);
                })
                .toList();

        // 2. Mapear DTOs con relaciones activas a tenants y offices (sin filtrar por cantidad)
        return eligibleUsers.stream()
                .map(user -> {
                    List<Long> tenantIds = userTenantRelationRepository.findById_UserId(user.getId())
                            .stream()
                            .filter(rel -> "ACTIVE".equals(rel.getStatus()))
                            .map(rel -> rel.getId().getTenantId())
                            .toList();

                    List<Long> officeIds = userOfficeRelationRepository.findById_UserId(user.getId())
                            .stream()
                            .filter(rel -> "ACTIVE".equals(rel.getStatus()))
                            .map(rel -> rel.getId().getOfficeId())
                            .toList();

                    return UserMapper.toUserDetailDTO(user, tenantIds, officeIds);
                })
                .toList();
    }



    public UserModel createUser(UserModel userModel) {
        ZoneId zoneId = ZoneId.systemDefault();
        System.out.println("Zona horaria actual: " + zoneId);

        if (userModel.getUsername() == null || userModel.getUsername().isEmpty()) {
            userModel.setUsername(userModel.getEmail());
        }

        if (userModel.getRole() == null) {
            RoleModel roleModel = new RoleModel();
            roleModel.setId(7L);
            userModel.setRole(roleModel);
        }

        if (userModel.getPassword() == null || userModel.getPassword().isEmpty()) {
            userModel.setPassword(PasswordGenerator.generatePassword(userModel.getName(),
                    userModel.getPhoneNumber(), userModel.getEmail()));
        }
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userModel.setActive(false);
        userModel.setEmailVerified(false);
        userModel.setCreatedAt(LocalDateTime.now());
        userModel.setTimeZone(String.valueOf(zoneId));
        userModel.setPreferredLanguage("ES");
        return iUserRepository.save(userModel);
    }

    public UserDetailDTO updateUser(Long userId, UserDetailDTO dto) {
        UserModel user = iUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setName(dto.getName());
        user.setIdNumber(dto.getIdNumber());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setCity(dto.getCity());
        user.setAddress(dto.getAddress());
        user.setAvatarUrl(dto.getAvatarUrl());
        user.setPreferredLanguage(dto.getPreferredLanguage());
        user.setTimeZone(dto.getTimeZone());

        if (dto.getRole() != null && dto.getRole().getId() != null) {
            RoleModel role = roleRepository.findById(dto.getRole().getId())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            user.setRole(role);
        }

        user.setUpdateAt(LocalDateTime.now());

        // Guardar datos básicos del usuario
        user = iUserRepository.save(user);

        // Actualizar relaciones con TENANTS
        if (dto.getTenantIds() != null) {
            userTenantRelationRepository.deleteById_UserId(userId);
            dto.getTenantIds().forEach(tenantId -> {
                UserTenantKey key = new UserTenantKey(userId, tenantId);
                UserTenantRelation relation = new UserTenantRelation(key, "TENANT_USER", "ACTIVE");
                userTenantRelationRepository.save(relation);
            });
        }

        // Actualizar relaciones con OFFICES
        if (dto.getOfficeIds() != null) {
            userOfficeRelationRepository.deleteById_UserId(userId);
            dto.getOfficeIds().forEach(officeId -> {
                UserOfficeKey key = new UserOfficeKey(userId, officeId);
                UserOfficeRelation relation = new UserOfficeRelation(key, "VETERINARIO", "ACTIVE");
                userOfficeRelationRepository.save(relation);
            });
        }

        // Obtener relaciones actualizadas
        List<Long> tenantIds = userTenantRelationRepository.findById_UserId(userId)
                .stream()
                .map(rel -> rel.getId().getTenantId())
                .toList();

        List<Long> officeIds = userOfficeRelationRepository.findById_UserId(userId)
                .stream()
                .map(rel -> rel.getId().getOfficeId())
                .toList();

        return UserMapper.toUserDetailDTO(user, tenantIds, officeIds);
    }



    @Override
    public List<UserModel> updateUserTenantManage(RequestUpdateTenantManager request) {
        Long tenantId = request.getTenantId();
        boolean isDeleted = request.isDeleted();
        List<Long> newManagerIds = request.getManagerIds();

        RoleModel roleUser = new RoleModel();
        roleUser.setId(7L); // Rol básico

        RoleModel roleManager = new RoleModel();
        roleManager.setId(3L); // Rol TENANT_ADMIN

        // Paso 1: Obtener relaciones actuales activas del tenant
        List<UserTenantRelation> currentManagers = userTenantRelationRepository.findById_TenantId(tenantId)
                .stream()
                .filter(rel -> "TENANT_ADMIN".equalsIgnoreCase(rel.getRole()))
                .filter(rel -> "ACTIVE".equalsIgnoreCase(rel.getStatus()))
                .toList();

        for (UserTenantRelation rel : currentManagers) {
            Long userId = rel.getId().getUserId();

            // Soft delete de la relación
            rel.setStatus("INACTIVE");
            userTenantRelationRepository.save(rel);

            // Verificar si ese usuario aún es admin en otro tenant
            boolean sigueSiendoAdmin = userTenantRelationRepository.findById_UserId(userId).stream()
                    .anyMatch(r ->
                            "TENANT_ADMIN".equalsIgnoreCase(r.getRole())
                                    && "ACTIVE".equalsIgnoreCase(r.getStatus())
                                    && !r.getId().getTenantId().equals(tenantId)
                    );

            // Si ya no administra ningún otro tenant, bajar el rol
            if (!sigueSiendoAdmin) {
                iUserRepository.findById(userId).ifPresent(user -> {
                    user.setRole(roleUser);
                    user.setUpdateAt(LocalDateTime.now());
                    iUserRepository.save(user);
                });
            }
        }

        List<UserModel> updatedManagers = new ArrayList<>();

        // Paso 2: Asignar nuevos managers (solo si no es eliminación)
        if (!isDeleted && newManagerIds != null) {
            for (Long userId : newManagerIds) {
                UserModel user = iUserRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                // Verifica si ya existe la relación activa
                Optional<UserTenantRelation> existing = userTenantRelationRepository
                        .findById(new UserTenantKey(userId, tenantId));

                if (existing.isPresent()) {
                    UserTenantRelation relation = existing.get();
                    relation.setStatus("ACTIVE");
                    relation.setRole("TENANT_ADMIN");
                    relation.setAssignedAt(LocalDateTime.now());
                    userTenantRelationRepository.save(relation);
                } else {
                    // Nueva relación
                    UserTenantKey key = new UserTenantKey(userId, tenantId);
                    UserTenantRelation newRelation = new UserTenantRelation(key, "TENANT_ADMIN", "ACTIVE");
                    userTenantRelationRepository.save(newRelation);
                }

                // Si el usuario no tiene el rol de admin, asignárselo
                if (user.getRole() == null || !user.getRole().getId().equals(roleManager.getId())) {
                    user.setRole(roleManager);
                    user.setUpdateAt(LocalDateTime.now());
                    iUserRepository.save(user);
                }

                updatedManagers.add(user);
            }
        }

        return updatedManagers;
    }




    @Override
    public UserModel updateUserOfficeManage(Long userId, Long officeId) {
        // Rol general para usuario normal
        RoleModel roleUser = new RoleModel();
        roleUser.setId(7L); // ID de usuario normal

        // Rol general para administrador de oficina
        RoleModel roleOfficeAdmin = new RoleModel();
        roleOfficeAdmin.setId(4L); // ID de OFFICE_ADMIN

        // Paso 1: Encontrar al usuario actual con rol OFFICE_ADMIN en esa oficina
        Optional<UserOfficeRelation> currentRelationOpt = userOfficeRelationRepository.findById_OfficeId(officeId)
                .stream()
                .filter(rel -> "OFFICE_ADMIN".equalsIgnoreCase(rel.getRole()))
                .findFirst();

        currentRelationOpt.ifPresent(relation -> {
            Long previousUserId = relation.getId().getUserId();

            // Eliminar la relación actual
            userOfficeRelationRepository.deleteById(relation.getId());

            // Cambiar rol global del usuario a "normal"
            UserModel previousUser = iUserRepository.findById(previousUserId)
                    .orElseThrow(() -> new RuntimeException("Usuario anterior no encontrado"));

            previousUser.setRole(roleUser);
            previousUser.setUpdateAt(LocalDateTime.now());

            iUserRepository.save(previousUser);
        });

        // Paso 2: Asignar nuevo usuario como administrador de oficina
        UserModel newAdmin = iUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crear nueva relación user ↔ office con rol OFFICE_ADMIN
        UserOfficeKey key = new UserOfficeKey(userId, officeId);
        UserOfficeRelation newRelation = new UserOfficeRelation(key, "OFFICE_ADMIN", "ACTIVE");
        userOfficeRelationRepository.save(newRelation);

        // Asignar rol global al usuario
        newAdmin.setRole(roleOfficeAdmin);
        newAdmin.setUpdateAt(LocalDateTime.now());

        return iUserRepository.save(newAdmin);
    }


    @Override
    public boolean deleteUserById(Long userId) {

        UserModel userFound = iUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        userFound.softDelete();
        iUserRepository.save(userFound);
        return true;
    }

    @Override
    public List<UserPublicDTO> getUsersByTenantId(Long tenantId) {
        List<Long> adminIds = userTenantRelationRepository.findAdminUserIdsByTenantId(tenantId);
        return iUserRepository.findAllById(adminIds).stream()
                .map(UserMapper::toUserPublicDTO)
                .toList();
    }


    @Override
    public Optional<UserModel> getUserByOfficeId(Long officeId) {
        return userOfficeRelationRepository.findOfficeAdminByOfficeId(officeId)
                .map(rel -> iUserRepository.findById(rel.getId().getUserId()))
                .orElse(Optional.empty());
    }

    public void approveVeterinarian(Long id) {
        UserModel userFound = iUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("USUARIO NO ENCONTRADO"));

        if (!userFound.getRole().getRoleName().equals("VETERINARIO")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "EL USUARIO NO ES UN VETERINARIO");
        }
        userFound.setActive(true);
        iUserRepository.save(userFound);
    }

}
