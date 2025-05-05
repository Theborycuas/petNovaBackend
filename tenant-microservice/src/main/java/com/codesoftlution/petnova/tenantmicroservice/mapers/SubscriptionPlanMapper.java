package com.codesoftlution.petnova.tenantmicroservice.mapers;

import com.codesoftlution.petnova.tenantmicroservice.dtos.SubscriptionPlanDTO;
import com.codesoftlution.petnova.tenantmicroservice.interfaces.ISubscriptionService;
import com.codesoftlution.petnova.tenantmicroservice.models.SubscriptionPlanModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubscriptionPlanMapper {


    private static ISubscriptionService subscriptionService = null;

    @Autowired
    public SubscriptionPlanMapper(ISubscriptionService service) {
        subscriptionService = service;
    }


    public static SubscriptionPlanDTO toSubscriptioPlanDTO(final SubscriptionPlanModel subscriptionPlanModel) {
        SubscriptionPlanDTO subscriptionPlanDTO = new SubscriptionPlanDTO();
        subscriptionPlanDTO.setId(subscriptionPlanModel.getId());
        subscriptionPlanDTO.setName(subscriptionPlanModel.getName());
        subscriptionPlanDTO.setInterval(String.valueOf(subscriptionPlanModel.getInterval()));
        subscriptionPlanDTO.setPriceUsd(subscriptionPlanModel.getPriceUsd());
        subscriptionPlanDTO.setFeatures(subscriptionPlanModel.getFeatures());
        subscriptionPlanDTO.setSubscribers(subscriptionService.getNumberOfSubscribers(subscriptionPlanModel.getId()));

        return subscriptionPlanDTO;
    }

    public static List<SubscriptionPlanDTO> toSubscriptionPlanDTOList(final List<SubscriptionPlanModel> subscriptionModelList) {
        List<SubscriptionPlanDTO> subscriptionPlanDTOList = new ArrayList<>();
        for (SubscriptionPlanModel subscriptionPlanModel : subscriptionModelList) {
            subscriptionPlanDTOList.add(toSubscriptioPlanDTO(subscriptionPlanModel));
        }
        return subscriptionPlanDTOList;
    }

}
