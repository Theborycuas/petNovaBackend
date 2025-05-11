package com.codesoftlution.petNova.user_microservice.controllers;

import com.codesoftlution.petNova.user_microservice.models.RoleModel;
import com.codesoftlution.petNova.user_microservice.respositories.IRoleRepository;
import com.codesoftlution.petNova.user_microservice.respositories.IUserRepository;
import com.codesoftlution.petNova.user_microservice.servicesImpl.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;


@RestController
@RequestMapping("apiPetNova/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    HttpServletRequest request;
    @Autowired
    IRoleRepository roleRepository;

    Logger log = Logger.getLogger(RoleController.class.getName());


    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    @RequestMapping(value = "/getAllRoles", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAllRoles() {
        log.info("START GET ALL USERS");
        List<RoleModel> roleModelList = roleService.getAllRoles();
        log.info("END GET ALL USERS");
        return new ResponseEntity<>(roleModelList, HttpStatus.OK);
    }

    @RequestMapping(value = "/getOk", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getOk() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }


}
