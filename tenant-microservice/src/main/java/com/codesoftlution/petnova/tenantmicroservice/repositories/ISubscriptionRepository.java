package com.codesoftlution.petnova.tenantmicroservice.repositories;

import com.codesoftlution.petnova.tenantmicroservice.models.SubscriptionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISubscriptionRepository extends JpaRepository<SubscriptionModel, Long> {
    List<SubscriptionModel> findByTenantId(Long tenantId);
}
