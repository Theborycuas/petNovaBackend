package com.codesoftlution.petnova.tenantmicroservice.servicesImpl;

import com.codesoftlution.petnova.tenantmicroservice.clientsfeign.OfficeFeignClient;
import com.codesoftlution.petnova.tenantmicroservice.clientsfeign.UserFeignClient;
import com.codesoftlution.petnova.tenantmicroservice.dtos.CreateTenantDTO;
import com.codesoftlution.petnova.tenantmicroservice.interfaces.ITenantService;
import com.codesoftlution.petnova.tenantmicroservice.models.TenantModel;
import com.codesoftlution.petnova.tenantmicroservice.repositories.ITenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static com.codesoftlution.petnova.tenantmicroservice.mapers.TenantMappers.toTenantModel;

@Service
public class TenantServiceImpl implements ITenantService {
    @Autowired
    ITenantRepository tenantRepository;

    @Autowired
    private OfficeFeignClient officeFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public TenantModel createTenant(String token, CreateTenantDTO createTenantDTO) {

        TenantModel tenantSaved = tenantRepository.save(toTenantModel(createTenantDTO));
        boolean userUpdate = userFeignClient
                .updateUserTenantManage(token, createTenantDTO.getManagerId(), tenantSaved.getId());

        if(!userUpdate) {
            throw new RuntimeException("User update failed");
        }

        return tenantSaved;
    }

    @Override
    public List<TenantModel> getAllTenants() {
        return tenantRepository.findAllByDeletedAtIsNull();
    }

    @Override
    public Optional<TenantModel> getTenantById(Long id) {
        return tenantRepository.findById(id);
    }

    @Override
    public boolean deleteTenantById(String token, Long tenantId) {

        TenantModel tenantFound = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));
        boolean officesDeleted = officeFeignClient.deleteOfficeByTenantId(token, tenantId);

        if (!officesDeleted) {
            throw new RuntimeException("ERROR AL ELIMINAR LOS CONSULTORIOS");
        }
        tenantFound.softDelete();
        tenantRepository.save(tenantFound);

        return true;
    }
}
