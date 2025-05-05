package com.codesoftlution.petnova.tenantmicroservice.servicesImpl;

import com.codesoftlution.petnova.tenantmicroservice.interfaces.ISubscriptionService;
import com.codesoftlution.petnova.tenantmicroservice.models.SubscriptionModel;
import com.codesoftlution.petnova.tenantmicroservice.repositories.ISubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class SubscriptionServiceImpl implements ISubscriptionService {

    @Autowired
    private ISubscriptionRepository repository;

    @Override
    public SubscriptionModel create(SubscriptionModel subscription) {
        return repository.save(subscription);
    }

    @Override
    public List<SubscriptionModel> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<SubscriptionModel> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<SubscriptionModel> findByTenantId(Long tenantId) {
        return repository.findByTenantId(tenantId);
    }

    @Override
    public SubscriptionModel update(Long id, SubscriptionModel updated) {
        return repository.findById(id).map(sub -> {
            sub.setPlan(updated.getPlan());
            sub.setStartDate(updated.getStartDate());
            sub.setEndDate(updated.getEndDate());
            sub.setStatus(updated.getStatus());
            return repository.save(sub);
        }).orElseThrow(() -> new RuntimeException("Subscription not found"));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
