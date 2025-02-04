package com.codesoflution.petNova.cita_microservice.services;

import com.codesoflution.petNova.cita_microservice.clientsfeign.UserFeignClient;
import com.codesoflution.petNova.cita_microservice.dtos.UserDTO;
import com.codesoflution.petNova.cita_microservice.models.CitaModel;
import com.codesoflution.petNova.cita_microservice.repositories.ICitaRepository;
import com.codesoflution.petNova.cita_microservice.response.AuthSesionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    @Autowired
    private ICitaRepository citaRepository;

    /*@Autowired
    RestTemplate restTemplate;*/

    @Autowired
    UserFeignClient userFeignClient;

    public boolean validateUserTokenActive(String token){
        ResponseEntity<AuthSesionResponse> response = userFeignClient.validateUserTokenActive(token);
        if(response.getStatusCode().is2xxSuccessful() && Boolean.TRUE.equals(response.getBody().isSuccess())){
            return true;
        }
        return false;
    }

    public CitaModel registrarCitas(CitaModel cita) {

        //USANDO FEIGN CLIENTE
        UserDTO userDTO = userFeignClient.getUserById(cita.getVeterinarioId());

        //USANDO RESTTEMPLATE
        //UserDTO userDTO = restTemplate.getForObject("http://localhost:8001/apiPetNova/users/getUserById/" + cita.getVeterinarioId(), UserDTO.class);


        //Validar que el Usuario sea de tipo VETERINARIO
        assert userDTO != null;
        if(!"VETERINARIO".equalsIgnoreCase(userDTO.getRollName())){
            throw new RuntimeException("El usuario asignado no tiene el rol de veterinario.");
        }

        // Validación: Verificamos si ya existe una cita en el mismo horario para el veterinario
        List<CitaModel> citas = citaRepository.findByVeterinarioIdAndFechaHoraBetween(
                cita.getVeterinarioId(),
                cita.getFechaHora().minusMinutes(30),
                cita.getFechaHora().plusMinutes(30)
        );
        if (!citas.isEmpty()) {
            throw new RuntimeException("EL VETERINARIO YA TIENE UNA CITA EN ESTE HORARIO");
        }

        return citaRepository.save(cita);
    }

    public List<CitaModel> listCitasByPets(Long idPet) {
        return citaRepository.findByPetId(idPet);
    }

    public CitaModel getCitaById(Long id) {
        return citaRepository.findById(id).orElseThrow(() -> new RuntimeException("La cita no existe"));
    }

    public CitaModel actualizarCita(Long id, CitaModel citaActualizada) {
        CitaModel cita = getCitaById(id);

        Optional.ofNullable(citaActualizada.getFechaHora()).ifPresent(cita::setFechaHora);
        Optional.ofNullable(citaActualizada.getMotivo()).ifPresent(cita::setMotivo);
        Optional.ofNullable(citaActualizada.getObsevations()).ifPresent(cita::setObsevations);
        Optional.ofNullable(citaActualizada.getVeterinarioId()).ifPresent(cita::setVeterinarioId);
        return citaRepository.save(cita);

    }

    public void eliminarCita(Long id) {
        CitaModel cita = getCitaById(id);

        cita.setActive(false);
        citaRepository.save(cita);
    }

}
