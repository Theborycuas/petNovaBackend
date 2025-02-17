package com.codesoftlution.petNova.pet_microservice.services;

import com.codesoftlution.petNova.pet_microservice.clientsfeign.UserFeignClient;
import com.codesoftlution.petNova.pet_microservice.dtos.UserDTO;
import com.codesoftlution.petNova.pet_microservice.models.PetModel;
import com.codesoftlution.petNova.pet_microservice.repositories.IPetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.codesoftlution.petNova.pet_microservice.utils.Constants.PREFIX_BEARER;

@Service
public class PetService {

    @Autowired
    private IPetRepository petRepository;

    @Autowired
    private UserFeignClient userFeignClient;

    public Optional<PetModel> findByNameAndSpecieIdAndUserId(String name, Long specieId, Long userId){
        return petRepository.findByNameAndSpecieIdAndUserId(name, specieId, userId);
    }

    public PetModel savePet(String token, PetModel petModel) {
        UserDTO userDTO = userFeignClient.getUserById(PREFIX_BEARER + token, petModel.getUserId());

        if(userDTO == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "USUARIO NO ENCONTRADO");
        }

        if(!userDTO.getRollName().equals("SUPER_ADMIN") && !Objects.equals(petModel.getUserId(), userDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "NO TIENES PERMISO PARA CREAR ESTA MASCOTA");
        }

        return petRepository.save(petModel);
    }

    public List<PetModel> getAllPetsByUser(Long userId) {
        return petRepository.findByUserId(userId);
    }

    public PetModel getPetById(Long id) {
        return petRepository.findById(id).orElseThrow(() -> new RuntimeException("Mascota no Encontrada"));
    }

    public PetModel updatePet(Long id, PetModel petModelUpdate) {
        PetModel petModel = getPetById(id);
        //Utilizo Optional.ofNullable reemplazando el if para comparar si cada atributo viene vacio
        Optional.ofNullable(petModelUpdate.getName()).ifPresent(petModel::setName);
        Optional.ofNullable(petModelUpdate.getRace()).ifPresent(petModel::setRace);
        Optional.ofNullable(petModelUpdate.getAge()).ifPresent(petModel::setAge);
        Optional.ofNullable(petModelUpdate.getColor()).ifPresent(petModel::setColor);
        Optional.of(petModelUpdate.isActive()).ifPresent(petModel::setActive);
        Optional.ofNullable(petModelUpdate.getObsevations()).ifPresent(petModel::setObsevations);
        return petRepository.save(petModel);
    }

    public void deletePet(Long id) {
        PetModel petModel = getPetById(id);
        petModel.setActive(false);
        petRepository.save(petModel);
    }

}
