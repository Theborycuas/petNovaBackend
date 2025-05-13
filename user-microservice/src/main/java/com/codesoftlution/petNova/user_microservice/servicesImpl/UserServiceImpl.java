package com.codesoftlution.petNova.user_microservice.servicesImpl;

import com.codesoftlution.petNova.user_microservice.clientsfeign.OfficeFeignClient;
import com.codesoftlution.petNova.user_microservice.dtos.UserDetailDTO;
import com.codesoftlution.petNova.user_microservice.interfaces.IUserServices;
import com.codesoftlution.petNova.user_microservice.mappers.UserMapper;
import com.codesoftlution.petNova.user_microservice.models.RoleModel;
import com.codesoftlution.petNova.user_microservice.models.UserModel;
import com.codesoftlution.petNova.user_microservice.request.RequestUpdateUser;
import com.codesoftlution.petNova.user_microservice.respositories.IRoleRepository;
import com.codesoftlution.petNova.user_microservice.respositories.IUserRepository;
import com.codesoftlution.petNova.user_microservice.utils.PasswordGenerator;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserServices {
    @Autowired
    IUserRepository iUserRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    OfficeFeignClient officeFeignClient;


    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public UserModel findByUsernameAndActive(String email, boolean active) {
        return iUserRepository.findByUsernameAndActive(email, active)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<UserDetailDTO> getAllUsers() {
        List<UserModel> userList = iUserRepository.findAllByDeletedAtIsNull();

        return userList.stream()
                .map(UserMapper::toUserDetailDTO)
                .collect(Collectors.toList());
    }

    public UserModel createUser(UserModel userModel) {
        ZoneId zoneId = ZoneId.systemDefault();
        System.out.println("Zona horaria actual: " + zoneId);

        if(userModel.getUsername() == null || userModel.getUsername().isEmpty()) {
            userModel.setUsername(userModel.getEmail());
        }

        if(userModel.getPassword() == null || userModel.getPassword().isEmpty()) {
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

    public UserModel updateUser(Long userId, UserDetailDTO dto) {
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
        user.setOfficeId(dto.getOfficeId());
        user.setTenantId(dto.getTenantId());

        if (dto.getRole() != null && dto.getRole().getId() != null) {
            RoleModel role = roleRepository.findById(dto.getRole().getId())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            user.setRole(role);
        }
        user.setUpdateAt(LocalDateTime.now());

        return iUserRepository.save(user);
    }

    public UserModel updateUserTenantManage(Long userId, Long tenantId) {
        RoleModel roleTenant = new RoleModel();
        roleTenant.setId(3L);

        UserModel user = iUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setTenantId(tenantId);
        user.setRole(roleTenant);
        user.setUpdateAt(LocalDateTime.now());

        return iUserRepository.save(user);
    }

    @Override
    public boolean deleteUserById(Long userId) {

        UserModel userFound = iUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        userFound.softDelete();
        iUserRepository.save(userFound);
        return true;
    }

    public void approveVeterinarian(Long id){
        UserModel userFound = iUserRepository.findById(id)
                .orElseThrow(()->new RuntimeException("USUARIO NO ENCONTRADO"));

        if(!userFound.getRole().getRoleName().equals("VETERINARIO")){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "EL USUARIO NO ES UN VETERINARIO");
        }
        userFound.setActive(true);
        iUserRepository.save(userFound);
    }

}
