package com.codesoftlution.petnova.tenantmicroservice.interfaces;

import com.codesoftlution.petnova.tenantmicroservice.models.InvoiceModel;

import java.util.List;
import java.util.Optional;

public interface IInvoiceService {
    InvoiceModel create(InvoiceModel invoice);
    Optional<InvoiceModel> findById(Long id);
    List<InvoiceModel> findAll();
    List<InvoiceModel> findByTenantId(Long tenantId);
    List<InvoiceModel> findBySubscriptionId(Long subscriptionId);
    InvoiceModel update(Long id, InvoiceModel invoice);
    void delete(Long id);
}
