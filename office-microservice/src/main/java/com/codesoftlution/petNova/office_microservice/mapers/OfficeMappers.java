package com.codesoftlution.petNova.office_microservice.mapers;

import com.codesoftlution.petNova.office_microservice.dtos.OfficeDTO;
import com.codesoftlution.petNova.office_microservice.dtos.OfficeDetailsDTO;
import com.codesoftlution.petNova.office_microservice.models.OfficeModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class OfficeMappers {

    public static OfficeDTO toOfficeDTO(OfficeModel model) {
        if (model == null) return null;

        OfficeDTO dto = new OfficeDTO();
        dto.setTenantId(model.getTenantId());
        dto.setName(model.getName());
        dto.setAddress(model.getAddress());
        dto.setCity(model.getCity());
        dto.setContactPhone(model.getContactPhone());
        dto.setTaxId(model.getTaxId());
        dto.setLogoUrl(model.getLogoUrl());
        dto.setContactEmail(model.getContactEmail());
        dto.setCurrentPlan(model.getCurrentPlan());

        return dto;
    }


    public static OfficeDetailsDTO toOfficeDetailsDTO(OfficeModel model, final List<Long> managerIds) {
        OfficeDetailsDTO dto = new OfficeDetailsDTO();

        dto.setId(model.getId());
        dto.setTenantId(model.getTenantId());
        dto.setName(model.getName());
        dto.setAddress(model.getAddress());
        dto.setCity(model.getCity());
        dto.setManagerIds(managerIds);
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

    public static OfficeModel toOfficeCreateModel(OfficeDTO dto) {
        if (dto == null) return null;

        OfficeModel model = new OfficeModel();

        model.setTenantId(dto.getTenantId());
        model.setName(dto.getName());
        model.setAddress(dto.getAddress());
        model.setCity(dto.getCity());
        model.setContactPhone(dto.getContactPhone());
        model.setTaxId(dto.getTaxId());
        model.setLogoUrl(dto.getLogoUrl());
        model.setContactEmail(dto.getContactEmail());
        model.setCurrentPlan(dto.getCurrentPlan());

        model.setActive(false);
        model.setEmailVerified(false);
        model.setCreatedAt(LocalDateTime.now());
        model.setSubscriptionStartDate(LocalDate.now());
        model.setSubscriptionEndDate(LocalDate.now().plusMonths(1));

        model.setPreferredLanguage("ES");
        model.setTimeZone(ZoneId.systemDefault().toString());
        model.setAllowsOnlineBooking(false);
        model.setOfficeHours("Lunes a Viernes 08:00 - 18:00");
        model.setLocationCoordinates("");

        return model;
    }

}
