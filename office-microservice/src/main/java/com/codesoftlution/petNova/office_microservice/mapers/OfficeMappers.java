package com.codesoftlution.petNova.office_microservice.mapers;

import com.codesoftlution.petNova.office_microservice.clientsfeign.UserFeignClient;
import com.codesoftlution.petNova.office_microservice.dtos.OfficeDetailsDTO;
import com.codesoftlution.petNova.office_microservice.dtos.UserPublicDTO;
import com.codesoftlution.petNova.office_microservice.models.OfficeModel;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;

public class OfficeMappers {

    public static OfficeDetailsDTO toOfficeDetailsDTO(OfficeModel model) {
        OfficeDetailsDTO dto = new OfficeDetailsDTO();

        dto.setId(model.getId());
        dto.setTenantId(model.getTenantId());
        dto.setName(model.getName());
        dto.setAddress(model.getAddress());
        dto.setContactPhone(model.getContactPhone());
        dto.setTaxId(model.getTaxId());
        dto.setLogoUrl(model.getLogoUrl());
        dto.setActive(model.isActive());
        dto.setContactEmail(model.getContactEmail());
        dto.setEmailVerified(model.isEmailVerified());
        dto.setCreatedAt(model.getCreatedAt());
        dto.setUpdatedAt(model.getUpdatedAt());
        dto.setCurrentPlan(model.getCurrentPlan());
        dto.setSubscriptionStartDate(model.getSubscriptionStartDate());
        dto.setSubscriptionEndDate(model.getSubscriptionEndDate());
        dto.setPreferredLanguage(model.getPreferredLanguage());
        dto.setTimeZone(model.getTimeZone());
        dto.setAllowsOnlineBooking(model.isAllowsOnlineBooking());
        dto.setOfficeHours(model.getOfficeHours());
        dto.setLocationCoordinates(model.getLocationCoordinates());

        return dto;
    }

    public static OfficeModel toOfficeModel(OfficeDetailsDTO dto) {
        OfficeModel model = new OfficeModel();
        model.setId(dto.getId());
        model.setTenantId(dto.getTenantId());
        model.setName(dto.getName());
        model.setAddress(dto.getAddress());
        model.setContactPhone(dto.getContactPhone());
        model.setTaxId(dto.getTaxId());
        model.setLogoUrl(dto.getLogoUrl());
        model.setActive(dto.isActive());
        model.setContactEmail(dto.getContactEmail());
        model.setEmailVerified(dto.isEmailVerified());
        model.setCreatedAt(dto.getCreatedAt());
        model.setUpdatedAt(dto.getUpdatedAt());
        model.setCurrentPlan(dto.getCurrentPlan());
        model.setSubscriptionStartDate(dto.getSubscriptionStartDate());
        model.setSubscriptionEndDate(dto.getSubscriptionEndDate());
        model.setPreferredLanguage(dto.getPreferredLanguage());
        model.setTimeZone(dto.getTimeZone());
        model.setAllowsOnlineBooking(dto.isAllowsOnlineBooking());
        model.setOfficeHours(dto.getOfficeHours());
        model.setLocationCoordinates(dto.getLocationCoordinates());

        return model;
    }

}
