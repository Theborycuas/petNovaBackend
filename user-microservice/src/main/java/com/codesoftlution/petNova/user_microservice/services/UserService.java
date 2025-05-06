package com.codesoftlution.petNova.user_microservice.services;

import com.codesoftlution.petNova.user_microservice.clientsfeign.OfficeFeignClient;
import com.codesoftlution.petNova.user_microservice.dtos.OfficeDTO;
import com.codesoftlution.petNova.user_microservice.dtos.UserDTO;
import com.codesoftlution.petNova.user_microservice.models.RoleModel;
import com.codesoftlution.petNova.user_microservice.models.UserModel;
import com.codesoftlution.petNova.user_microservice.request.RequestUpdateUser;
import com.codesoftlution.petNova.user_microservice.respositories.IRoleRepository;
import com.codesoftlution.petNova.user_microservice.respositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.codesoftlution.petNova.user_microservice.mappers.UserMapper.toUserModel;

@Service
public class UserService {
    @Autowired
    IUserRepository iUserRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    OfficeFeignClient officeFeignClient;


    public UserModel findByUsernameAndActive(String email, boolean active) {
        return iUserRepository.findByUsernameAndActive(email, active)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<UserModel> getAllUsers() {
        return iUserRepository.findAll();
    }

    public UserModel createUser(UserDTO userDTO) {
        Optional<UserModel> userFound = iUserRepository.findByUsernameAndActive(userDTO.getUsername(), true);
        if (!userFound.isPresent()) {
            return iUserRepository.save(toUserModel(userDTO));
        }else{
            return null;
        }

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
        Optional.ofNullable(userUpdate.getLinkPerfilPhoto()).ifPresent(userFound::setLinkPerfilPhoto);

        LocalDateTime dateNow = LocalDateTime.now();
        userFound.setUpdateDate(dateNow);
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
