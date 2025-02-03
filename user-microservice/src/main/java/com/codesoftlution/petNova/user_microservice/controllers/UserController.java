package com.codesoftlution.petNova.user_microservice.controllers;

import com.codesoftlution.petNova.user_microservice.mappers.UserMapper;
import com.codesoftlution.petNova.user_microservice.models.RoleModel;
import com.codesoftlution.petNova.user_microservice.models.UserModel;
import com.codesoftlution.petNova.user_microservice.response.ListUserResponse;
import com.codesoftlution.petNova.user_microservice.response.ResponseDataUserUpdate;
import com.codesoftlution.petNova.user_microservice.respositories.IRoleRepository;
import com.codesoftlution.petNova.user_microservice.respositories.IUserRepository;
import com.codesoftlution.petNova.user_microservice.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static com.codesoftlution.petNova.user_microservice.mappers.UserMapper.toUserDTO;
import static com.codesoftlution.petNova.user_microservice.utils.Constants.*;


@RestController
@RequestMapping("apiPetNova/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    HttpServletRequest request;
    @Autowired
    IRoleRepository roleRepository;

    Logger log = Logger.getLogger(UserController.class.getName());

    @RequestMapping(value = "/userRegister", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity userRegister(@Valid @RequestBody UserModel userModel) {
        try {
            log.info("START USER REGISTER");
            UserModel usuarioEncontrado = userService.findUserByEmail(userModel.getUsername(), true);
            if (usuarioEncontrado == null) {
                if(userModel.getOfficeId() != null) {
                    RoleModel rolEncontrado = roleRepository.findById(userModel.getRole().getId())
                            .orElseThrow(()->new RuntimeException("No se encontro el role"));
                    if (rolEncontrado.getRoleName().equals("VETERINARIO")){
                        userService.registerSetUser(userModel);
                    } else {
                        throw new RuntimeException("Solo los usuarios con rol de VETERINARIO pueden estar asociados a un consultorio.");
                    }
                }else {
                    userService.registerSetUser(userModel);
               }
                log.info("END USER REGISTER");
                return new ResponseEntity("USUARIO CREADO", HttpStatus.CREATED);
            } else {
                return new ResponseEntity("USUARIO NO CREADO", HttpStatus.CONFLICT);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/getUserByToken/{userToken}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUserByToken(@PathVariable("userToken") String userToken) {
        try {
            log.info("START USER GET USER BY TOKEN: ");
            UserModel userFound = userService.findUserByTokenAndActive(userToken, true);
            if (userFound != null) {
                log.info("END USER GET USER BY TOKEN: ");
                return new ResponseEntity(userFound, HttpStatus.OK);
            } else {
                return new ResponseEntity("USUARIO NO ENCONTRADO", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/getUserById/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUserById(@PathVariable("userId") Long userId) {
        try {
            log.info("START USER GET USER BY ID: ");
            UserModel userFound = userRepository.findById(userId)
                    .orElseThrow(()->new RuntimeException("No se encontro el usuario"));
            log.info("END USER GET USER BY ID: ");

            return new ResponseEntity(toUserDTO(userFound), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity userUpdate(@Valid @RequestBody ResponseDataUserUpdate dataUserUpdate) {
        try {
            log.info("START USER UPDATE");
            String token = request.getHeader("PnAuthorization");

            UserModel usuarioEncontrado = userService.findUserByTokenAndActive(token, true);
            if (usuarioEncontrado != null) {
                //Utilizo Optional.ofNullable reemplazando el if para comparar si cada atributo viene vacio
                Optional.ofNullable(dataUserUpdate.getName()).ifPresent(usuarioEncontrado::setName);
                Optional.ofNullable(dataUserUpdate.getUsername()).ifPresent(usuarioEncontrado::setUsername);
                Optional.ofNullable(dataUserUpdate.getEmail()).ifPresent(usuarioEncontrado::setEmail);
                Optional.ofNullable(dataUserUpdate.getIdNumber()).ifPresent(usuarioEncontrado::setIdNumber);
                Optional.ofNullable(dataUserUpdate.getPhoneNumber()).ifPresent(usuarioEncontrado::setPhoneNumber);
                Optional.ofNullable(dataUserUpdate.getLinkPerfilPhoto()).ifPresent(usuarioEncontrado::setLinkPerfilPhoto);

                userService.registerSetUser(usuarioEncontrado);
                log.info("END USER UPDATE");
                return new ResponseEntity("USUARIO ACTUALIZADO", HttpStatus.OK);
            } else {
                return new ResponseEntity("USUARIO NO ENCONTRADO", HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/getUsers", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getUsers() {
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
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity deleteUser() {
        try {
            log.info("START DELETEUSER");
            String token = request.getHeader("PnAuthorization");
            UserModel usuarioEncontrado = userService.findUserByTokenAndActive(token, true);
            if (usuarioEncontrado != null) {
                usuarioEncontrado.setActive(false);
                userService.registerSetUser(usuarioEncontrado);

                log.info("END DELETEUSER");
                return new ResponseEntity("USUARIO ELIMINADO", HttpStatus.OK);
            } else {
                return new ResponseEntity("USUARIO NO ENCONTRADO", HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/pnCifrado/{token}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String pnCifrado(@PathVariable("token") String token) {
        try {
            log.info("START PNCIFRADO");
            IvParameterSpec iv = new IvParameterSpec(PN_INIT_VECTOR.getBytes(STANDARDCHARSETS_UFT_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(PN_AES_KEY.getBytes(STANDARDCHARSETS_UFT_8), ALGORITHM_AES);
            Cipher cipher;
            cipher = Cipher.getInstance(TRANSFORMATION_AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);

            byte[] encrypted = cipher.doFinal(token.getBytes());

            log.info("END PNCIFRADO");
            return Base64.getUrlEncoder()
                    .encodeToString(encrypted);

        } catch (UnsupportedEncodingException | NoSuchPaddingException | NoSuchAlgorithmException |
                 IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException |
                 InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/pnDescifrado/{tokenCifrado}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String pnDescifrado(@PathVariable("tokenCifrado") String token) {
        try {
            log.info("START PNDESCIFRADO");
            IvParameterSpec iv = new IvParameterSpec(PN_INIT_VECTOR.getBytes(STANDARDCHARSETS_UFT_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(PN_AES_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM_AES);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION_AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);

            byte[] desencriptado = cipher.doFinal(Base64.getUrlDecoder().decode(token));
            log.info("END PNDESCIFRADO");
            return new String(desencriptado);
        } catch (InvalidAlgorithmParameterException | UnsupportedEncodingException | NoSuchPaddingException |
                 IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/getOk", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getOk() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }



}
