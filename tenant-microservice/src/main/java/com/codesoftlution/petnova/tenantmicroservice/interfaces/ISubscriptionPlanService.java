package com.codesoftlution.petnova.tenantmicroservice.interfaces;

import com.codesoftlution.petnova.tenantmicroservice.models.SubscriptionPlanModel;

import java.util.List;
import java.util.Optional;

public interface ISubscriptionPlanService {
    SubscriptionPlanModel create(SubscriptionPlanModel plan);
    Optional<SubscriptionPlanModel> findById(Long id);
    List<SubscriptionPlanModel> findAll();
    SubscriptionPlanModel update(Long id, SubscriptionPlanModel plan);
    void delete(Long id);
}
