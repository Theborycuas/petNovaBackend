package com.codesoflution.petNova.cita_microservice.controllers;

import com.codesoflution.petNova.cita_microservice.clientsfeign.UserFeignClient;
import com.codesoflution.petNova.cita_microservice.models.CitaModel;
import com.codesoflution.petNova.cita_microservice.services.CitaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("apiPetNova/citas")
@CrossOrigin("*")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @Autowired
    HttpServletRequest request;

    Logger log = Logger.getLogger(this.getClass().getName());

    @RequestMapping(value = "/citaRegister/{userId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity citaRegister(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody CitaModel citaModel,
            @PathVariable Long userId) {
        try {
            log.info("START CITA REGISTER");
            if(!citaService.validateUserTokenActive(token)){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("TOKEN INVALIDO");
            }
            citaService.registrarCitas(citaModel);
            log.info("END CITA REGISTER");
            return new ResponseEntity("CITA REGISTRADA", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/getCitasListByPets/{petId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCitasListByPets(@PathVariable("petId") Long petId) {
        log.info("START GET CITAS LIST BY PETS");
        List<CitaModel> citaModelList = citaService.listCitasByPets(petId);
        log.info("END GET CITAS LIST BY PETS");
        return new ResponseEntity(citaModelList, HttpStatus.OK);
    }

    @RequestMapping(value = "/getCitaById/{citaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCitaById(@PathVariable("citaId") Long citaId) {
        try {
            log.info("START GET CITA BY ID");
            CitaModel citaModel = citaService.getCitaById(citaId);
            log.info("END GET CITA BY ID");
            return new ResponseEntity(citaModel, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/updateCita/{citaId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateCita(@Valid @PathVariable("citaId") Long citaId, @Valid @RequestBody CitaModel citaModel) {

        try {
            log.info("START UPDATE CITA BY ID");
            citaService.actualizarCita( citaId, citaModel);
            log.info("END UPDATE CITA BY ID");
            return new ResponseEntity("CITA ACTUALIZADA", HttpStatus.OK);

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @RequestMapping(value = "/deleteCita/{citaId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteCita(@PathVariable("citaId") Long citaId) {

        try {
            log.info("START DELETE CITA BY ID");
            citaService.eliminarCita(citaId);
            log.info("END DELETE CITA BY ID");
            return new ResponseEntity("CITA ELIMINADA", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

}
