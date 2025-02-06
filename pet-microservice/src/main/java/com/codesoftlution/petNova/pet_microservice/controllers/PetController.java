package com.codesoftlution.petNova.pet_microservice.controllers;

import com.codesoftlution.petNova.pet_microservice.models.PetModel;
import com.codesoftlution.petNova.pet_microservice.services.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("apiPetNova/pets")
@CrossOrigin("*")
public class PetController {

   @Autowired
   PetService petService;

    Logger log = Logger.getLogger(PetController.class.getName());

   @RequestMapping(value = "/petRegister", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity petRegister(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody PetModel petModel) {

        try {
            log.info("START PETREGISTER");
            Optional<PetModel> mascotaEncontrada = petService.findByNameAndSpecieIdAndUserId(petModel.getName(), petModel.getSpecie().getId(), petModel.getUserId());
            if (!mascotaEncontrada.isPresent()) {
                petService.savePet(token, petModel);
                log.info("END PETREGISTER");
                return new ResponseEntity("MASCOTA CREADA", HttpStatus.CREATED);
            } else {
                return new ResponseEntity("MASCOTA ENCONTRADA", HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/getPetsByUser/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPetsByUser(@PathVariable("userId") Long userId) {
        try {

            log.info("START GET PETS BY USER");

            List<PetModel> petModelList = petService.getAllPetsByUser(userId);

            log.info("END GET PETS BY USER");
            return new ResponseEntity(petModelList, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/getPetById/{petId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPetById(@PathVariable("petId") Long petId) {
        try {
            log.info("START GET PET BY ID");
            PetModel petModel = petService.getPetById(petId);
            log.info("END GET PET BY ID");
            return new ResponseEntity(petModel, HttpStatus.OK);

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "updatePet/{petId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updatePet(@PathVariable("petId") Long petId, @RequestBody PetModel petModel) {
        try {
            log.info("START UPDATE PET");
            petService.updatePet(petId, petModel);
            log.info("ENS UPDATE PET");
            return new ResponseEntity("MASCOTA ACTUALIZADA", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @RequestMapping(value = "deletePet/{petId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deletePet(@PathVariable("petId") Long petId) {
        try {
            log.info("START DELETE PET");
            petService.deletePet(petId);
            log.info("END DELETE PET");
            return new ResponseEntity("MASCOTA ELIMINADA", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
}
