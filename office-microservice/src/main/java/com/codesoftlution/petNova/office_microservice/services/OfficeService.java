package com.codesoftlution.petNova.office_microservice.services;


import com.codesoftlution.petNova.office_microservice.clientsfeign.UserFeignClient;
import com.codesoftlution.petNova.office_microservice.config.JwtUtil;
import com.codesoftlution.petNova.office_microservice.dtos.OfficeDTO;
import com.codesoftlution.petNova.office_microservice.models.OfficeModel;
import com.codesoftlution.petNova.office_microservice.repositories.IOfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OfficeService {
    @Autowired
    private IOfficeRepository officeRepository;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private JwtUtil jwtUtil;

    public OfficeModel officeRegister(String token, OfficeModel officeModel) {
        String roleSuperAdmin = jwtUtil.extractRole(token);

        if (roleSuperAdmin.equals("SUPER_ADMIN")) {
            officeModel.setTenantId(1L);
            officeModel.setCreatedAt(LocalDateTime.now());
            officeModel.setSubscriptionStartDate(LocalDate.now());

            if (officeModel.getCurrentPlan().equals(1L)) {
                officeModel.setSubscriptionEndDate(LocalDate.now().plusDays(30));
            } else {
                officeModel.setSubscriptionEndDate(LocalDate.now().plusDays(30));
            }
            return officeRepository.save(officeModel);
        }
        throw new RuntimeException("No tienes permisos para registrar el Offices");
    }

    public List<OfficeModel> getAllOffices() {
        return officeRepository.findAllByDeletedAtIsNull();
    }

    public Optional<OfficeModel> getOfficeById(Long officeId) {
        return officeRepository.findById(officeId);
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
        if (officeDTO.getPhoneNumber() != null) {
            officeFound.setContactPhone(officeDTO.getPhoneNumber());
        }

        officeFound.setUpdatedAt(LocalDateTime.now());

        if(officeDTO.getManagerId() != null) {
            boolean userUpdate = userFeignClient.updateUserOfficeManage(
                    token, officeDTO.getManagerId(), officeId);
            if(!userUpdate) {
                throw new RuntimeException("User update failed");
            }
        }
        return officeRepository.save(officeFound);
    }

    public OfficeModel deleteOfficeById(Long officeId) {

        OfficeModel consultorioFound = officeRepository.findById(officeId)
                .orElseThrow(() -> new RuntimeException("Consultorio no Encontrado"));
        consultorioFound.softDelete();

        return officeRepository.save(consultorioFound);
    }

    public void deleteOfficeByTenantId(Long tenantId) {
        List<OfficeModel> consultoriosEncontrados = officeRepository.findAllByTenantIdAndDeletedAtIsNull(tenantId);

        for (OfficeModel consultorio : consultoriosEncontrados) {
            consultorio.softDelete();
            officeRepository.save(consultorio);
        }
    }

}
