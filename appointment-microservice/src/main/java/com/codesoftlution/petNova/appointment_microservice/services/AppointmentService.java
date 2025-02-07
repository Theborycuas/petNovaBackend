package com.codesoftlution.petNova.appointment_microservice.services;

import com.codesoftlution.petNova.appointment_microservice.clientsfeign.PetFeignClient;
import com.codesoftlution.petNova.appointment_microservice.clientsfeign.UserFeignClient;
import com.codesoftlution.petNova.appointment_microservice.dtos.PetDTO;
import com.codesoftlution.petNova.appointment_microservice.dtos.UserDTO;
import com.codesoftlution.petNova.appointment_microservice.models.AppointmentModel;
import com.codesoftlution.petNova.appointment_microservice.repositories.IAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private IAppointmentRepository citaRepository;
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private PetFeignClient petFeignClient;

    public AppointmentModel registrarCitas(String token, AppointmentModel appointmentModel) {

        //Validar que el Usuario sea de tipo VETERINARIO

        UserDTO userDTO = userFeignClient
                .getUserById("Bearer " + token, appointmentModel.getVeterinarioId());

        PetDTO petDTO = petFeignClient.
                getPetById("Bearer " + token, appointmentModel.getPetId());

        if(petDTO == null) {
            throw new RuntimeException("MASCOTA NO ENCONTRADA");
        }

        if(userDTO != null) {
            if(!"VETERINARIO".equalsIgnoreCase(userDTO.getRollName())){
                throw new RuntimeException("El usuario asignado no tiene el rol de veterinario.");
            }
        }else {
            throw new RuntimeException("USUARIO NO ENCONTRADA");
        }

        /*UserModel userVeterinario = userRepository.findById(cita.getVeterinario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
         if(!"VETERINARIO".equalsIgnoreCase(userVeterinario.getRole().getRoleName())){
            throw new RuntimeException("El usuario asignado no tiene el rol de veterinario.");
        }*/

        // Validación: Verificamos si ya existe una cita en el mismo horario para el veterinario
        List<AppointmentModel> citas = citaRepository.findByVeterinarioIdAndFechaHoraBetween(
                appointmentModel.getVeterinarioId(),
                appointmentModel.getFechaHora().minusMinutes(30),
                appointmentModel.getFechaHora().plusMinutes(30)
        );
        if (!citas.isEmpty()) {
            throw new RuntimeException("EL VETERINARIO YA TIENE UNA CITA EN ESTE HORARIO");
        }

        return citaRepository.save(appointmentModel);
    }

    public List<AppointmentModel> listCitasByPets(Long idPet) {
        return citaRepository.findByPetId(idPet);
    }

    public AppointmentModel getCitaById(Long id) {
        return citaRepository.findById(id).orElseThrow(() -> new RuntimeException("La cita no existe"));
    }

    public AppointmentModel actualizarCita(Long id, AppointmentModel citaActualizada) {
        AppointmentModel cita = getCitaById(id);

        Optional.ofNullable(citaActualizada.getFechaHora()).ifPresent(cita::setFechaHora);
        Optional.ofNullable(citaActualizada.getMotivo()).ifPresent(cita::setMotivo);
        Optional.ofNullable(citaActualizada.getObsevations()).ifPresent(cita::setObsevations);
        Optional.ofNullable(citaActualizada.getVeterinarioId()).ifPresent(cita::setVeterinarioId);
        return citaRepository.save(cita);

    }

    public void eliminarCita(Long id) {
        AppointmentModel cita = getCitaById(id);

        cita.setActive(false);
        citaRepository.save(cita);
    }

}
