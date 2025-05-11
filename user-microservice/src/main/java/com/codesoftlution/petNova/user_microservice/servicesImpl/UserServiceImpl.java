package com.codesoftlution.petNova.user_microservice.servicesImpl;

import com.codesoftlution.petNova.user_microservice.clientsfeign.OfficeFeignClient;
import com.codesoftlution.petNova.user_microservice.interfaces.IUserServices;
import com.codesoftlution.petNova.user_microservice.models.UserModel;
import com.codesoftlution.petNova.user_microservice.request.RequestUpdateUser;
import com.codesoftlution.petNova.user_microservice.respositories.IRoleRepository;
import com.codesoftlution.petNova.user_microservice.respositories.IUserRepository;
import com.codesoftlution.petNova.user_microservice.utils.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

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

    public List<UserModel> getAllUsers() {
        return iUserRepository.findAll();
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

    public UserModel updateUser(String token, RequestUpdateUser userUpdate) {


        UserModel userFound = iUserRepository.findByUsernameAndActive(userUpdate.getUsername(), true)
                .orElseThrow(() -> new RuntimeException("USUARIO NO ENCONTRADO"));

        //Utilizo Optional.ofNullable reemplazando el if para comparar si cada atributo viene vacio
        Optional.ofNullable(userUpdate.getName()).ifPresent(userFound::setName);
        Optional.ofNullable(userUpdate.getUsername()).ifPresent(userFound::setUsername);
        Optional.ofNullable(userUpdate.getEmail()).ifPresent(userFound::setEmail);
        Optional.ofNullable(userUpdate.getIdNumber()).ifPresent(userFound::setIdNumber);
        Optional.ofNullable(userUpdate.getPhoneNumber()).ifPresent(userFound::setPhoneNumber);
        Optional.ofNullable(userUpdate.getAvatarUrl()).ifPresent(userFound::setAvatarUrl);

        LocalDateTime dateNow = LocalDateTime.now();
        userFound.setUpdateAt(dateNow);
        return iUserRepository.save(userFound);
    }

    /*public void deleteUser(String token) {

        UserModel userFound = iUserRepository.findByUsernameAndActive(userName, true)
                .orElseThrow(()-> new RuntimeException("USUARIO NO ENCONTRADO"));

        userFound.setActive(false);
        iUserRepository.save(userFound);
    }*/

    /*public void deleteUserByAdmin(String adminToken, String userName) {

        String userNameAdmin = jwtService.extractUsername(adminToken.substring(7));

        UserModel adminUserFound = iUserRepository.findByUsernameAndActive(userNameAdmin, true)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "USUARIO NO ENCONTRADO"));

        if(!adminUserFound.getRole().getRoleName().equals("SUPER_ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "SOLO ADMINISTRADORES PUEDEN ELIMINAR CUENTAS");
        }

        UserModel userToDelete = iUserRepository.findByUsernameAndActive(userName, true)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "USUARIO NO ENCONTRADO"));

        if(userToDelete.getRole().getRoleName().equals("SUPER_ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "NO PUEDES ELIMINAR UNA CUENTA SUPERADMIN");
        }

        userToDelete.setActive(false);
        iUserRepository.save(userToDelete);
    }*/

    public void approveVeterinarian(Long id){
        UserModel userFound = iUserRepository.findById(id)
                .orElseThrow(()->new RuntimeException("USUARIO NO ENCONTRADO"));

        if(!userFound.getRole().getRoleName().equals("VETERINARIO")){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "EL USUARIO NO ES UN VETERINARIO");
        }
        userFound.setActive(true);
        iUserRepository.save(userFound);
    }



 /*   @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return iUserRepository.findByUsernameAndActive(username, true)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario no existe o no esta Activo"));
    }*/
}
