package com.codesoftlution.petnova.tenantmicroservice.servicesImpl;

import com.codesoftlution.petnova.tenantmicroservice.interfaces.ISubscriptionPlanService;
import com.codesoftlution.petnova.tenantmicroservice.models.SubscriptionPlanModel;
import com.codesoftlution.petnova.tenantmicroservice.repositories.ISubscriptionPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class SubscriptionPlanServiceImpl implements ISubscriptionPlanService {

    private final ISubscriptionPlanRepository repository;

    @Autowired
    public SubscriptionPlanServiceImpl(ISubscriptionPlanRepository repository) {
        this.repository = repository;
    }

    @Override
    public SubscriptionPlanModel create(SubscriptionPlanModel plan) {
        return repository.save(plan);
    }

    @Override
    public Optional<SubscriptionPlanModel> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<SubscriptionPlanModel> findAll() {
        return repository.findAll();
    }

    @Override
    public SubscriptionPlanModel update(Long id, SubscriptionPlanModel updatedPlan) {
        return repository.findById(id)
                .map(plan -> {
                    plan.setName(updatedPlan.getName());
                    plan.setPriceUsd(updatedPlan.getPriceUsd());
                    plan.setInterval(updatedPlan.getInterval());
                    plan.setFeatures(updatedPlan.getFeatures());
                    return repository.save(plan);
                })
                .orElseThrow(() -> new RuntimeException("Plan no encontrado: " + id));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
