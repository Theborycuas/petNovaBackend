package com.codesoftlution.petNova.pet_microservice.mappers;

import com.codesoftlution.petNova.pet_microservice.dtos.PetDTO;
import com.codesoftlution.petNova.pet_microservice.models.PetModel;

public class PetMapper {

    public static PetDTO toPetDTO(final PetModel petModel) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(petModel.getId());
        petDTO.setName(petModel.getName());
        petDTO.setRace(petModel.getRace());
        petDTO.setAge(petModel.getAge());
        petDTO.setColor(petModel.getColor());
        petDTO.setActive(petModel.isActive());
        petDTO.setObsevations(petModel.getObsevations());
        petDTO.setSpecieName(petModel.getSpecie().getName());
        petDTO.setUserId(petModel.getUserId());

        return petDTO;

    }
}
