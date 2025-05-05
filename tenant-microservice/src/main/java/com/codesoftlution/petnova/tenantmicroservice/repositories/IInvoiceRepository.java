package com.codesoftlution.petnova.tenantmicroservice.repositories;

import com.codesoftlution.petnova.tenantmicroservice.models.InvoiceModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IInvoiceRepository extends JpaRepository<InvoiceModel, Long> {
    List<InvoiceModel> findByTenantId(Long tenantId);
    List<InvoiceModel> findBySubscriptionId(Long subscriptionId);
}
