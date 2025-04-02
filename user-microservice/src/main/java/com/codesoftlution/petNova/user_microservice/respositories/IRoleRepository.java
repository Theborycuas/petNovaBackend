package com.codesoftlution.petNova.user_microservice.respositories;



import com.codesoftlution.petNova.user_microservice.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<RoleModel, Long> {
    boolean existsByRoleName(String rolName);
    RoleModel findByRoleName(String roleName);

}
