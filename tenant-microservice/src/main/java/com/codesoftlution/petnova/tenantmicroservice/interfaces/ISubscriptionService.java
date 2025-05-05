package com.codesoftlution.petnova.tenantmicroservice.interfaces;

import com.codesoftlution.petnova.tenantmicroservice.models.SubscriptionModel;

import java.util.List;
import java.util.Optional;

public interface ISubscriptionService {
    SubscriptionModel create(SubscriptionModel subscription);
    List<SubscriptionModel> findAll();
    Optional<SubscriptionModel> findById(Long id);
    List<SubscriptionModel> findByTenantId(Long tenantId);
    SubscriptionModel update(Long id, SubscriptionModel subscription);
    void delete(Long id);
}
