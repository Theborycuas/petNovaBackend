package com.codesoftlution.petNova.appointment_microservice.repositories;

import com.codesoftlution.petNova.appointment_microservice.models.AppointmentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentRepository extends JpaRepository<AppointmentModel, Long> {
    List<AppointmentModel> findByPetId(Long petId);
    List<AppointmentModel> findByVeterinarioIdAndFechaHoraBetween(Long veterinarioId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
