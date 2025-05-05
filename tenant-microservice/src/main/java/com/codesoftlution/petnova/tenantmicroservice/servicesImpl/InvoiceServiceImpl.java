package com.codesoftlution.petnova.tenantmicroservice.servicesImpl;

import com.codesoftlution.petnova.tenantmicroservice.interfaces.IInvoiceService;
import com.codesoftlution.petnova.tenantmicroservice.models.InvoiceModel;
import com.codesoftlution.petnova.tenantmicroservice.repositories.IInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class InvoiceServiceImpl implements IInvoiceService {

    @Autowired
    private IInvoiceRepository repository;

    @Override
    public InvoiceModel create(InvoiceModel invoice) {
        return repository.save(invoice);
    }

    @Override
    public Optional<InvoiceModel> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<InvoiceModel> findAll() {
        return repository.findAll();
    }

    @Override
    public List<InvoiceModel> findByTenantId(Long tenantId) {
        return repository.findByTenantId(tenantId);
    }

    @Override
    public List<InvoiceModel> findBySubscriptionId(Long subscriptionId) {
        return repository.findBySubscriptionId(subscriptionId);
    }

    @Override
    public InvoiceModel update(Long id, InvoiceModel updated) {
        return repository.findById(id).map(invoice -> {
            invoice.setAmount(updated.getAmount());
            invoice.setDueDate(updated.getDueDate());
            invoice.setPaidDate(updated.getPaidDate());
            invoice.setStatus(updated.getStatus());
            invoice.setNotes(updated.getNotes());
            return repository.save(invoice);
        }).orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}

