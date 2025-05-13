package com.codesoftlution.petNova.user_microservice.servicesImpl;

import com.codesoftlution.petNova.user_microservice.models.RoleModel;
import com.codesoftlution.petNova.user_microservice.respositories.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    IRoleRepository roleRepository;


    public List<RoleModel> getAllRoles() {
        return roleRepository.findAll();
    }

}
