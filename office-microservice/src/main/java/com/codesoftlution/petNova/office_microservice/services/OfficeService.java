package com.codesoftlution.petNova.office_microservice.services;


import com.codesoftlution.petNova.office_microservice.clientsfeign.UserFeignClient;
import com.codesoftlution.petNova.office_microservice.dtos.OfficeDTO;
import com.codesoftlution.petNova.office_microservice.dtos.OfficeDetailsDTO;
import com.codesoftlution.petNova.office_microservice.dtos.UserPublicDTO;
import com.codesoftlution.petNova.office_microservice.mapers.OfficeMappers;
import com.codesoftlution.petNova.office_microservice.models.OfficeModel;
import com.codesoftlution.petNova.office_microservice.repositories.IOfficeRepository;
import com.codesoftlution.petNova.office_microservice.request.RequestUpdateOfficeManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.codesoftlution.petNova.office_microservice.mapers.OfficeMappers.toOfficeCreateModel;

@Service
public class OfficeService {
    @Autowired
    private IOfficeRepository officeRepository;

    @Autowired
    private UserFeignClient userFeignClient;

    public OfficeModel createOffice(String token, OfficeDTO officeDTO) {

        OfficeModel officeModel = officeRepository.save(toOfficeCreateModel(officeDTO));

        RequestUpdateOfficeManager requestUpdateOfficeManager = new RequestUpdateOfficeManager();
        requestUpdateOfficeManager.setOfficeId(officeModel.getId());
        requestUpdateOfficeManager.setManagerIds(officeDTO.getManagerIds());
        requestUpdateOfficeManager.setDeleted(false);

        boolean userUpdate = userFeignClient
                .updateUserOfficeManage(token, requestUpdateOfficeManager);

        if (!userUpdate) {
            throw new RuntimeException("User update failed");
        }

        return officeModel;
    }

    public List<OfficeModel> getAllOffices() {
        return officeRepository.findAllByDeletedAtIsNull();
    }

    public List<OfficeDetailsDTO> getAllOfficesDTO(String token) {

        List<OfficeModel> offices = officeRepository.findAllByDeletedAtIsNull();
        List<OfficeDetailsDTO> officeListDTO = new ArrayList<>();

        for (OfficeModel office : offices) {
            OfficeDetailsDTO dto = new OfficeDetailsDTO();
            try {
                List<UserPublicDTO> users = userFeignClient.getUsersByOfficeId(token, office.getId());
                if (users != null) {
                    List<Long> managerIds = users.stream()
                            .map(UserPublicDTO::getId)
                            .collect(Collectors.toList());
                    dto = OfficeMappers.toOfficeDetailsDTO(office, managerIds);
                } else {
                    dto = OfficeMappers.toOfficeDetailsDTO(office, null);
                }
            } catch (Exception e) {
                dto = OfficeMappers.toOfficeDetailsDTO(office, null);
            }
            officeListDTO.add(dto);
        }

        return officeListDTO;
    }

    public Optional<OfficeModel> getOfficeById(Long officeId) {
        return officeRepository.findById(officeId);
    }

    public OfficeDetailsDTO getOfficeDetailById(String token, Long officeId) {
        // 1. Buscar la oficina
        OfficeModel office = officeRepository.findById(officeId)
                .orElseThrow(() -> new EntityNotFoundException("Office no encontrada con id: " + officeId));

        // 2. Llamar al feign client para traer los usuarios asignados a esa office
        List<UserPublicDTO> userList = userFeignClient.getUsersByOfficeId(token, officeId);

        // 3. Filtrar los administradores por rol y obtener sus IDs
        List<Long> managerIds = userList.stream()
                .map(UserPublicDTO::getId)
                .collect(Collectors.toList());

        // 4. Mapear y retornar el DTO
        return OfficeMappers.toOfficeDetailsDTO(office, managerIds);
    }


    public List<OfficeModel> getOfficesByTenantId(Long tenantId) {
        return officeRepository.findAllByTenantIdAndDeletedAtIsNull(tenantId);
    }


    public OfficeModel updateOffice(String token, Long officeId, OfficeDTO officeDTO) {

        OfficeModel officeFound = officeRepository.findById(officeId)
                .orElseThrow(() -> new RuntimeException("Consultorio no Encontrado"));

        if (officeDTO.getName() != null) {
            officeFound.setName(officeDTO.getName());
        }
        if (officeDTO.getAddress() != null) {
            officeFound.setAddress(officeDTO.getAddress());
        }
        if (officeDTO.getCity() != null) {
            officeFound.setCity(officeDTO.getCity());
        }
        if (officeDTO.getContactEmail() != null) {
            officeFound.setContactEmail(officeDTO.getContactEmail());
        }
        if (officeDTO.getContactPhone() != null) {
            officeFound.setContactPhone(officeDTO.getContactPhone());
        }
        if (officeDTO.getCurrentPlan() != null) {
            officeFound.setCurrentPlan(officeDTO.getCurrentPlan());
        }

        officeFound.setUpdatedAt(LocalDateTime.now());

        RequestUpdateOfficeManager requestUpdateOfficeManager = new RequestUpdateOfficeManager();
        requestUpdateOfficeManager.setOfficeId(officeId);
        requestUpdateOfficeManager.setManagerIds(officeDTO.getManagerIds());
        requestUpdateOfficeManager.setDeleted(false);

        if (officeDTO.getManagerIds() != null) {
            boolean userUpdate = userFeignClient.updateUserOfficeManage(
                    token, requestUpdateOfficeManager);

            if (!userUpdate) {
                throw new RuntimeException("User update failed");
            }
        }
        return officeRepository.save(officeFound);
    }

    public boolean deleteOfficeById(String token, Long officeId) {

        OfficeModel consultorioFound = officeRepository.findById(officeId)
                .orElseThrow(() -> new RuntimeException("Consultorio no Encontrado"));

        RequestUpdateOfficeManager requestUpdateOfficeManager = new RequestUpdateOfficeManager();
        requestUpdateOfficeManager.setOfficeId(officeId);
        requestUpdateOfficeManager.setManagerIds(Collections.singletonList(0L));
        requestUpdateOfficeManager.setDeleted(true);

        boolean userUpdate = userFeignClient
                .updateUserOfficeManage(token, requestUpdateOfficeManager);


        if (!userUpdate) {
            throw new RuntimeException("User update failed");
        }
        consultorioFound.softDelete();
        officeRepository.save(consultorioFound);

        return true;
    }

    public void deleteOfficeByTenantId(Long tenantId) {
        List<OfficeModel> consultoriosEncontrados = officeRepository.findAllByTenantIdAndDeletedAtIsNull(tenantId);

        for (OfficeModel consultorio : consultoriosEncontrados) {
            consultorio.softDelete();
            officeRepository.save(consultorio);
        }
    }

}
