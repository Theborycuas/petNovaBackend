package com.codesoftlution.petnova.tenantmicroservice.repositories;

import com.codesoftlution.petnova.tenantmicroservice.models.SubscriptionPlanModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISubscriptionPlanRepository extends JpaRepository<SubscriptionPlanModel, Long> {
}
