package com.codesoftlution.petnova.tenantmicroservice.servicesImpl;

import com.codesoftlution.petnova.tenantmicroservice.clientsfeign.OfficeFeignClient;
import com.codesoftlution.petnova.tenantmicroservice.clientsfeign.UserFeignClient;
import com.codesoftlution.petnova.tenantmicroservice.dtos.TenantDTO;
import com.codesoftlution.petnova.tenantmicroservice.dtos.TenantDetailDTO;
import com.codesoftlution.petnova.tenantmicroservice.dtos.UserPublicDTO;
import com.codesoftlution.petnova.tenantmicroservice.interfaces.ITenantService;
import com.codesoftlution.petnova.tenantmicroservice.models.TenantModel;
import com.codesoftlution.petnova.tenantmicroservice.repositories.ITenantRepository;
import com.codesoftlution.petnova.tenantmicroservice.request.RequestUpdateTenantManager;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.codesoftlution.petnova.tenantmicroservice.mapers.TenantMappers.*;

@Service
public class TenantServiceImpl implements ITenantService {
    Logger log = Logger.getLogger(TenantServiceImpl.class.getName());

    @Autowired
    ITenantRepository tenantRepository;

    @Autowired
    private OfficeFeignClient officeFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public TenantModel createTenant(String token, TenantDTO tenantDTO) {

        TenantModel tenantSaved = tenantRepository.save(toTenantModel(tenantDTO));

        RequestUpdateTenantManager requestUpdateTenantManager = new RequestUpdateTenantManager();
        requestUpdateTenantManager.setTenantId(tenantSaved.getId());
        requestUpdateTenantManager.setManagerIds(tenantDTO.getManagerIds());
        requestUpdateTenantManager.setDeleted(false);

        boolean userUpdate = userFeignClient
                .updateUserTenantManage(token, requestUpdateTenantManager);

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
    public TenantDetailDTO getTenantById( String token, Long id) {
        // 1. Buscar el tenant
        TenantModel tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tenant no encontrado con id: " + id));

        // 2. Llamar al feign client para traer los usuarios del tenant
        List<UserPublicDTO> userList = userFeignClient.getUsersByTenantId(token, id);

        // 3. Filtrar los administradores y extraer sus IDs
        List<Long> managerIds = userList.stream()
               /* .filter(user -> List.of("TENANT_ADMIN", "ADMIN")
                        .contains(user.getRoleName().toUpperCase()))*/
                .map(UserPublicDTO::getId)
                .collect(Collectors.toList());

        // 4. Mapear a DTO
        return toTenantDetailDTO(tenant, managerIds);
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

        RequestUpdateTenantManager requestUpdateTenantManager = new RequestUpdateTenantManager();
        requestUpdateTenantManager.setTenantId(tenantFound.getId());
        requestUpdateTenantManager.setManagerIds(tenantDTO.getManagerIds());
        requestUpdateTenantManager.setDeleted(false);

        if(tenantDTO.getManagerIds() != null) {
            boolean userUpdate = userFeignClient
                    .updateUserTenantManage(token, requestUpdateTenantManager);

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
        RequestUpdateTenantManager requestUpdateTenantManager = new RequestUpdateTenantManager();
        requestUpdateTenantManager.setTenantId(tenantId);
        requestUpdateTenantManager.setManagerIds(Collections.singletonList(0L));
        requestUpdateTenantManager.setDeleted(true);

        boolean userUpdate = userFeignClient
                .updateUserTenantManage(token, requestUpdateTenantManager);

        if(!userUpdate) {
            throw new RuntimeException("User update failed");
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

        try {
            List<UserPublicDTO> userPublicDTO = userFeignClient.getUsersByTenantId(token, id);

            List<Long> managerIds = userPublicDTO.stream()
                    /* .filter(user -> List.of("TENANT_ADMIN", "ADMIN")
                             .contains(user.getRoleName().toUpperCase()))*/
                    .map(UserPublicDTO::getId)
                    .collect(Collectors.toList());
            tenantDTO.setManagerIds(managerIds);

        } catch (FeignException.NotFound e) {
            // Usuario no encontrado: dejar managerId como null
        } catch (FeignException e) {
            log.warning("Error al obtener usuario por tenantId desde userFeignClient: " + e.getMessage());
        }

        return tenantDTO;
    }
}
