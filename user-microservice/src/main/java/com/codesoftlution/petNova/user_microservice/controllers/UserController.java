package com.codesoftlution.petNova.user_microservice.controllers;

import com.codesoftlution.petNova.user_microservice.mappers.UserMapper;
import com.codesoftlution.petNova.user_microservice.models.UserModel;
import com.codesoftlution.petNova.user_microservice.request.RequestUpdateUser;
import com.codesoftlution.petNova.user_microservice.respositories.IRoleRepository;
import com.codesoftlution.petNova.user_microservice.respositories.IUserRepository;
import com.codesoftlution.petNova.user_microservice.servicesImpl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

import static com.codesoftlution.petNova.user_microservice.mappers.UserMapper.toUserDetailDTO;
import static com.codesoftlution.petNova.user_microservice.mappers.UserMapper.toUserPublicDTO;
import static com.codesoftlution.petNova.user_microservice.servicesImpl.CifradoAESService.pnCifradoService;
import static com.codesoftlution.petNova.user_microservice.servicesImpl.CifradoAESService.pnDescifradoService;


@RestController
@RequestMapping("apiPetNova/users")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    HttpServletRequest request;
    @Autowired
    IRoleRepository roleRepository;

    Logger log = Logger.getLogger(UserController.class.getName());

    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAllUsers() {
        log.info("START GET ALL USERS");
        List<UserModel> userModelList = userServiceImpl.getAllUsers();
        log.info("END GET ALL USERS");
        return new ResponseEntity<>(userModelList, HttpStatus.OK);
    }

    @RequestMapping(value = "/getUserDetailById/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserDetailById(@PathVariable("userId") Long userId) {
        log.info("START GET USER DETAILS BY ID: ");
        UserModel userFound = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario"));
        log.info("END GET USER DETAILS BY ID: ");
        return ResponseEntity.status(HttpStatus.OK).body(toUserDetailDTO(userFound));
    }

    @RequestMapping(value = "/getUserPublicById/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserPublicById(@PathVariable("userId") Long userId) {
        log.info("START GET USER PUBLIC BY ID: ");
        UserModel userFound = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario"));
        log.info("END GET USER PUBLIC BY ID: ");
        return ResponseEntity.status(HttpStatus.OK).body(toUserPublicDTO(userFound));
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> userCreate(
            @Valid @RequestBody UserModel userModel) {
        log.info("START USER CREATE USER: ");
        UserModel createdUser = userServiceImpl.createUser(userModel);
        log.info("END USER CREATE USER: ");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }


    @RequestMapping(value = "/updateUser", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> userUpdate(
            @Valid @RequestHeader("Authorization") String token,
            @Valid @RequestBody RequestUpdateUser requestUpdateUser) {
        try {
            log.info("START UPDATE USER: ");
            UserModel updatedUser = userServiceImpl.updateUser(token, requestUpdateUser);
            log.info("END UPDATE USER: ");
            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

   /* @RequestMapping(value = "/getUsers", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getUsers() {
        try {
            log.info("START GETUSERS");
            List<UserModel> userModelList = userService.getUsers();

            ListUserResponse listUserResponse = new ListUserResponse();
            listUserResponse.setUserModels(userModelList);
            listUserResponse.setMessage("Lista de Usuarios");

            log.info("END GETUSERS");
            return new ResponseEntity<>(listUserResponse, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @RequestMapping(value = "/deleteMyAccount", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deleteMyAccount(
            @Valid @RequestHeader("Authorization") String token
    ) {
        try {
            log.info("START DELETE MY ACCOUNT");
            //userService.deleteUser(token);
            log.info("END DELETE MY ACCOUNT");
            return ResponseEntity.status(HttpStatus.OK).body("USUARIO ELIMINADO");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/deleteUserByAdmin", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUserByAdmin(String adminToken, String usernameToDelete) {
        try {
            log.info("START DELETE USER BY ADMIN");
            //userService.deleteUserByAdmin(adminToken, usernameToDelete);
            log.info("END DELETE USER BY ADMIN");
            return ResponseEntity.status(HttpStatus.OK).body("USUARIO ELIMINADO");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @RequestMapping(value = "/approveVeterinarian/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> approveVeterinarian(@Valid @PathVariable Long id) {
        try {
            log.info("START APPROVE VTERINARIAN");
            userServiceImpl.approveVeterinarian(id);
            log.info("END APPROVE VTERINARIAN");
            return ResponseEntity.status(HttpStatus.OK).body("VETERINARIO ACTIVADO");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/pnCifrado/{stringDesCifrado}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> pnCifrado(@PathVariable("stringDesCifrado") String stringDesCifrado) {
        try {
            log.info("START PNCIFRADO");
            String stringCifrado = pnCifradoService(stringDesCifrado);
            log.info("END PNCIFRADO");
            return ResponseEntity.status(HttpStatus.OK).body(stringCifrado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/pnDescifrado/{stringCifrado}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> pnDescifrado(@PathVariable("stringCifrado") String stringCifrado) {
        try {
            log.info("START PNDESCIFRADO");
            String stringDescifrado = pnDescifradoService(stringCifrado);
            log.info("END PNDESCIFRADO");
            return ResponseEntity.status(HttpStatus.OK).body(stringDescifrado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/getOk", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getOk() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }


}
