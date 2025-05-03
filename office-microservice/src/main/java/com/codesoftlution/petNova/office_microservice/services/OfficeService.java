package com.codesoftlution.petNova.office_microservice.services;


import com.codesoftlution.petNova.office_microservice.clientsfeign.UserFeignClient;
import com.codesoftlution.petNova.office_microservice.config.JwtUtil;
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

        if(roleSuperAdmin.equals("SUPER_ADMIN")){
            officeModel.setTenantId(1L);
            officeModel.setCreatedAt(LocalDateTime.now());
            officeModel.setSubscriptionStartDate(LocalDate.now());

            if(officeModel.getCurrentPlan().equals(1L)){
                officeModel.setSubscriptionEndDate(LocalDate.now().plusDays(30));
            } else {
                officeModel.setSubscriptionEndDate(LocalDate.now().plusDays(30));
            }
            return officeRepository.save(officeModel);
        }
        throw new RuntimeException("No tienes permisos para registrar el Offices");

        //Validar que exista el ususario que crea el consultorio

       /* UserDTO userDTO = userFeignClient.getUserById("Bearer " + token, userId);

        if(!"SUPER_ADMIN".equalsIgnoreCase(userDTO.getRollName()) &&
                !"VETERINARIO".equalsIgnoreCase(userDTO.getRollName()) &&
                !"OFFICE_ADMIN".equalsIgnoreCase(userDTO.getRollName())){
            throw new RuntimeException("NO TIENS PERMISOS PARA CREAR CONSULTORIOS.");
        }

        //Validar Único nombre y telefono
        if(officeRepository.existsByName(officeModel.getName())){
            throw new RuntimeException("El consultorio ya existe");
        }
        if(officeRepository.existsByPhoneNumber(officeModel.getPhoneNumber())){
            throw new RuntimeException("El consultorio ya existe");
        }

        //Asociar al veterinario si es incluido
       if(veterinarioId != null){
            UserDTO veterinario = userFeignClient.getUserById("Bearer " + token, veterinarioId);

            if(!"VETERINARIO".equalsIgnoreCase(veterinario.getRollName())){
                throw new RuntimeException("El Usuario asignado como responsable no es un Veterinario");
            }
            officeModel.setVeterinarioId(veterinario.getId());
        }

        //Guardar y retornar el Consultorio
        return officeRepository.save(officeModel);*/
    }

    public List<OfficeModel> getAllOffices() {
        return officeRepository.findAll();
    }

    public Optional<OfficeModel> getOfficeById(Long officeId) {
        return officeRepository.findById(officeId);
    }


    public List<OfficeModel> getOfficesByTenantId(Long tenantId) {
        return officeRepository.findByTenantId(tenantId);
    }


    public OfficeModel updateOffice(Long officeId, Long userId, OfficeModel officeModel) {

        /*UserModel userModel = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no Encontrado"));

        //Validar que se tenga el Rol para crear consultorio
        if(!"VETERINARIO".equalsIgnoreCase(userModel.getRole().getRoleName()) && !"OFFICE_ADMIN".equalsIgnoreCase(userModel.getRole().getRoleName())){
            throw new RuntimeException("Solo un administrador o un veterinario pueden registrar consultorios.");
        }*/

        OfficeModel consultorioEncontrado = officeRepository.findById(officeId)
                .orElseThrow(() -> new RuntimeException("Consultorio no Encontrado"));

        Optional.ofNullable(officeModel.getName()).ifPresent(consultorioEncontrado::setName);
        Optional.ofNullable(officeModel.getContactPhone()).ifPresent(consultorioEncontrado::setContactPhone);
        Optional.ofNullable(officeModel.getAddress()).ifPresent(consultorioEncontrado::setAddress);
        Optional.ofNullable(officeModel.getLogoUrl()).ifPresent(consultorioEncontrado::setLogoUrl);

        return officeRepository.save(consultorioEncontrado);
    }

    public OfficeModel deleteOfficeById(Long officeId) {

        OfficeModel consultorioFound = officeRepository.findById(officeId)
                .orElseThrow(() -> new RuntimeException("Consultorio no Encontrado"));
        consultorioFound.softDelete();

        return officeRepository.save(consultorioFound);
    }
}
