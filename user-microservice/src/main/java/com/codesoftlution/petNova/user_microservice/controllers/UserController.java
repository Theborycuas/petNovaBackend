package com.codesoftlution.petNova.user_microservice.controllers;

import com.codesoftlution.petNova.user_microservice.dtos.UserDetailDTO;
import com.codesoftlution.petNova.user_microservice.dtos.UserPublicDTO;
import com.codesoftlution.petNova.user_microservice.models.UserModel;
import com.codesoftlution.petNova.user_microservice.request.RequestUpdateOfficeManager;
import com.codesoftlution.petNova.user_microservice.request.RequestUpdateTenantManager;
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
import java.util.Optional;
import java.util.logging.Logger;

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
        List<UserDetailDTO> userDetailDTOList = userServiceImpl.getAllUsers();
        log.info("END GET ALL USERS");
        return new ResponseEntity<>(userDetailDTOList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    @RequestMapping(value = "/getEligibleUsersForAssignment", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getEligibleUsersForAssignment(
            @RequestParam String context
    ) {
        log.info("START GET ALL USERS");
        List<UserDetailDTO> userDetailDTOList = userServiceImpl.getEligibleUsersForAssignment(context);
        log.info("END GET ALL USERS");
        return new ResponseEntity<>(userDetailDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/getUserDetailById/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserDetailById(@PathVariable("userId") Long userId) {
        log.info("START GET USER DETAILS BY ID: ");
        UserDetailDTO userDTO = userServiceImpl.getUserDetailById(userId);
        log.info("END GET USER DETAILS BY ID: ");
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
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

    @RequestMapping(value = "/updateUserDetailById/{userId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(
            @PathVariable("userId") Long userId,
            @Valid @RequestBody UserDetailDTO userDTO
    ) {
        log.info("START UPDATE USER");
        UserDetailDTO updatedDTO = userServiceImpl.updateUser(userId, userDTO);
        log.info("END UPDATE USER");
        return ResponseEntity.status(HttpStatus.OK).body(updatedDTO);
    }

    @RequestMapping(value = "/deleteUserById/{userId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUserById(@PathVariable("userId") Long userId) {
        log.info("START DELETE USER BY ID: ");
        boolean userDelete = userServiceImpl.deleteUserById(userId);
        log.info("END DELETE USER BY ID: ");
        return new ResponseEntity<>(userDelete, HttpStatus.OK);
    }

    @RequestMapping(value = "/updateUserTenantManage", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUserTenantManage(
            @Valid @RequestBody RequestUpdateTenantManager requestUpdateTenantManager
    ) {
        log.info("START UPDATE USER TENANT MANAGER");
        userServiceImpl.updateUserTenantManage(requestUpdateTenantManager);
        log.info("END UPDATE USER TENANT MANAGER");
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @RequestMapping(value = "/getUsersByTenantId/{tenantId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsersByTenantId(@PathVariable("tenantId") Long tenantId) {
        log.info("START GET USER PUBLIC BY TENANT ID: ");
        List<UserPublicDTO> usersFound = userServiceImpl.getUsersByTenantId(tenantId);
        log.info("END GET USER PUBLIC BY TENANT ID: ");
        return ResponseEntity.ok(usersFound);

    }

    @RequestMapping(value = "/getUserByOfficeId/{officeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserByOfficeId(@PathVariable("officeId") Long officeId) {
        log.info("START GET USER PUBLIC BY OFFICE ID: ");
        Optional<UserModel> userFound = userServiceImpl.getUserByOfficeId(officeId);
        log.info("END GET USER PUBLIC BY OFFICE ID: ");

        if (userFound.isPresent()) {
            return ResponseEntity.ok(toUserPublicDTO(userFound.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @RequestMapping(value = "/getUsersByOfficeId/{officeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsersByOfficeId(@PathVariable("officeId") Long officeId) {
        log.info("START GET USER PUBLIC BY TENANT ID: ");
        List<UserPublicDTO> usersFound = userServiceImpl.getUsersByOfficeId(officeId);
        log.info("END GET USER PUBLIC BY TENANT ID: ");
        return ResponseEntity.ok(usersFound);

    }

    @RequestMapping(value = "/updateUserOfficeManage", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUserOfficeManage(
            @Valid @RequestBody RequestUpdateOfficeManager requestUpdateOfficeManager
    ) {
        log.info("START UPDATE USER TENANT MANAGER");
        userServiceImpl.updateUserOfficeManage(requestUpdateOfficeManager);
        log.info("END UPDATE USER TENANT MANAGER");
        return ResponseEntity.status(HttpStatus.OK).body(true);
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
