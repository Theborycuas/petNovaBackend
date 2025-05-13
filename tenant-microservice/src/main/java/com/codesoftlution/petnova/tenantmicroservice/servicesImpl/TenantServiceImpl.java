package com.codesoftlution.petnova.tenantmicroservice.servicesImpl;

import com.codesoftlution.petnova.tenantmicroservice.clientsfeign.OfficeFeignClient;
import com.codesoftlution.petnova.tenantmicroservice.clientsfeign.UserFeignClient;
import com.codesoftlution.petnova.tenantmicroservice.dtos.TenantDTO;
import com.codesoftlution.petnova.tenantmicroservice.dtos.UserPublicDTO;
import com.codesoftlution.petnova.tenantmicroservice.interfaces.ITenantService;
import com.codesoftlution.petnova.tenantmicroservice.models.TenantModel;
import com.codesoftlution.petnova.tenantmicroservice.repositories.ITenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.codesoftlution.petnova.tenantmicroservice.mapers.TenantMappers.toTenantDTO;
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
    public TenantModel createTenant(String token, TenantDTO tenantDTO) {

        TenantModel tenantSaved = tenantRepository.save(toTenantModel(tenantDTO));
        boolean userUpdate = userFeignClient
                .updateUserTenantManage(token, tenantDTO.getManagerId(), tenantSaved.getId());

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
    public TenantModel updateTenantById(String token, Long id, TenantDTO tenantDTO) {
        TenantModel tenantFound = tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenant no Encontrado"));

        if (tenantDTO.getTenantName() != null) {
            tenantFound.setTenantName(tenantDTO.getTenantName());
        }
        if (tenantDTO.getAddress() != null) {
            tenantFound.setAddress(tenantDTO.getAddress());
        }
        if (tenantDTO.getCity() != null) {
            tenantFound.setCity(tenantDTO.getCity());
        }
        if (tenantDTO.getContactEmail() != null) {
            tenantFound.setContactEmail(tenantDTO.getContactEmail());
        }
        if (tenantDTO.getContactPhone() != null) {
            tenantFound.setContactPhone(tenantDTO.getContactPhone());
        }
        if (tenantDTO.getPlanId() != null) {
            tenantFound.setPlanId(tenantDTO.getPlanId());
        }
        if (tenantDTO.getCurrency() != null) {
            tenantFound.setCurrency(tenantDTO.getCurrency());
        }
        tenantFound.setUpdatedAt(LocalDateTime.now());

        if(tenantDTO.getManagerId() != null) {
            boolean userUpdate = userFeignClient
                    .updateUserTenantManage(token, tenantDTO.getManagerId(), tenantFound.getId());

            if(!userUpdate) {
                throw new RuntimeException("User update failed");
            }
        }
        return tenantRepository.save(tenantFound);
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

    @Override
    public TenantDTO getBasicTenantById(String token, Long id) {
        TenantModel tenantModel = tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenant no Encontrado"));

        TenantDTO tenantDTO = toTenantDTO(tenantModel);
        UserPublicDTO userPublicDTO = userFeignClient.getUserByTenantId(token, id);

        tenantDTO.setManagerId(userPublicDTO.getId());

        return tenantDTO;
    }
}
