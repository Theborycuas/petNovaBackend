package com.codesoftlution.petNova.user_microservice;

import com.codesoftlution.petNova.user_microservice.models.RoleModel;
import com.codesoftlution.petNova.user_microservice.respositories.IRoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInit {
    private final IRoleRepository iRoleRepository;

    @Autowired
    public DataInit(IRoleRepository iRoleRepository) {
        this.iRoleRepository = iRoleRepository;
    }



    @PostConstruct
    public void createDataPetNova() {

        //CREATED DATA INIT ROLS
        if (!iRoleRepository.existsByRoleName("SUPER_ADMIN")) {
            RoleModel role1 = new RoleModel();
            role1.setRoleName("SUPER_ADMIN");
            role1.setDescription("Usuario SUPER_ADMIN");
            iRoleRepository.save(role1);
        }
        if (!iRoleRepository.existsByRoleName("ADMIN")) {
            RoleModel role2 = new RoleModel();
            role2.setRoleName("ADMIN");
            role2.setDescription("Usuario ADMIN");
            iRoleRepository.save(role2);
        }
        if (!iRoleRepository.existsByRoleName("TENANT_ADMIN")) {
            RoleModel role3 = new RoleModel();
            role3.setRoleName("TENANT_ADMIN");
            role3.setDescription("Usuario TENANT_ADMIN");
            iRoleRepository.save(role3);
        }
        if (!iRoleRepository.existsByRoleName("OFFICE_ADMIN")) {
            RoleModel role4 = new RoleModel();
            role4.setRoleName("OFFICE_ADMIN");
            role4.setDescription("Usuario OFFICE_ADMIN");
            iRoleRepository.save(role4);
        }
        if (!iRoleRepository.existsByRoleName("VETERINARIO")) {
            RoleModel role5 = new RoleModel();
            role5.setRoleName("VETERINARIO");
            role5.setDescription("Usuario VETERINARIO");
            iRoleRepository.save(role5);
        }
        if (!iRoleRepository.existsByRoleName("RECEPCIONISTA")) {
            RoleModel role6 = new RoleModel();
            role6.setRoleName("RECEPCIONISTA");
            role6.setDescription("Usuario RECEPCIONISTA");
            iRoleRepository.save(role6);
        }
        if (!iRoleRepository.existsByRoleName("USUARIO")) {
            RoleModel role7 = new RoleModel();
            role7.setRoleName("USUARIO");
            role7.setDescription("Usuario USUARIO");
            iRoleRepository.save(role7);
        }
    }
}
