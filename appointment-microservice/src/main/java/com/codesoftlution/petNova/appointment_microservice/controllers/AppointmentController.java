package com.codesoftlution.petNova.appointment_microservice.controllers;

import com.codesoftlution.petNova.appointment_microservice.models.AppointmentModel;
import com.codesoftlution.petNova.appointment_microservice.services.AppointmentService;
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
@RequestMapping("apiPetNova/appointments")
@CrossOrigin("*")
public class AppointmentController {

    @Autowired
    private AppointmentService citaService;

    @Autowired
    HttpServletRequest request;

    Logger log = Logger.getLogger(this.getClass().getName());

    @RequestMapping(value = "/citaRegister", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity citaRegister(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody AppointmentModel citaModel) {
        try {
            log.info("START CITA REGISTER");
            citaService.registrarCitas(token, citaModel);
            log.info("END CITA REGISTER");
            return new ResponseEntity("CITA RESGISTRADA", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/getCitasListByPets/{petId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCitasListByPets(@PathVariable("petId") Long petId) {
        log.info("START GET CITAS LIST BY PETS");
        List<AppointmentModel> citaModelList = citaService.listCitasByPets(petId);
        if(citaModelList == null || citaModelList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NO HAY CITAS PARA ESTA MASCOTA");
        }
        log.info("END GET CITAS LIST BY PETS");
        return new ResponseEntity(citaModelList, HttpStatus.OK);
    }

    @RequestMapping(value = "/getCitaById/{citaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCitaById(@PathVariable("citaId") Long citaId) {
        try {
            log.info("START GET CITA BY ID");
            AppointmentModel citaModel = citaService.getCitaById(citaId);
            log.info("END GET CITA BY ID");
            return new ResponseEntity(citaModel, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/updateCita/{citaId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateCita(@Valid @PathVariable("citaId") Long citaId, @Valid @RequestBody AppointmentModel citaModel) {

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
