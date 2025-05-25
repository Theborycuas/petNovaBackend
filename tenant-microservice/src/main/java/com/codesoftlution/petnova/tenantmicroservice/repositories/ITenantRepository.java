package com.codesoftlution.petnova.tenantmicroservice.repositories;

import com.codesoftlution.petnova.tenantmicroservice.models.TenantModel;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ITenantRepository extends JpaRepository<TenantModel, Long> {
    List<TenantModel> findAllByDeletedAtIsNull();

    @Query("SELECT t FROM TenantModel t " +
            "WHERE (t.emailVerified = false " +
            "OR t.subscriptionStartDate IS NULL " +
            "OR t.subscriptionEndDate IS NULL " +
            "OR t.active = false) " +
            "AND t.createdAt >= :startDate")
    List<TenantModel> findInactiveTenantsSince(@Param("startDate") LocalDateTime startDate);
}
