package com.example.yogastudioproject.service;

import com.example.yogastudioproject.domain.model.Client;
import com.example.yogastudioproject.domain.model.Subscription;
import com.example.yogastudioproject.repository.SubscriptionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Clock;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepo subscriptionRepo;
    private final CompanyService companyService;
    private final ClientService clientService;


    public Subscription getSubscriptionById(Long subscriptionId, Principal principal) {
        Subscription subscription = subscriptionRepo.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Sub not found"));

        if (!isBelongCompany(subscription, principal))
            throw new RuntimeException("");

        return subscription;
    }

    public Subscription createSubscription(Subscription subscription, Long clientId, Principal principal) {
        Client client = clientService.getClientById(clientId, principal);

        subscription.setClient(client);
        subscription.setCompany(companyService.getCompanyByPrincipal(principal));

        return subscriptionRepo.save(subscription);
    }

    public boolean isActive(Subscription subscription) {                                                       // Need check time format
        LocalDate now = LocalDate.now(Clock.systemDefaultZone());
        return now.isAfter(subscription.getOpenedAt()) &&
                now.isBefore(subscription.getFinishedAt()) &&
                subscription.getNumberOfClasses() > 0;
    }


    public void deleteSubscription(Long subscriptionId, Principal principal) {
        Subscription subscription = subscriptionRepo.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Sub not found"));

        if (!isBelongCompany(subscription, principal))
            throw new RuntimeException("This subscription cannot be deleted!");

        subscriptionRepo.delete(subscription);
    }

    private boolean isBelongCompany(Subscription subscription, Principal principal) {
        return subscription.getCompany().equals(companyService.getCompanyByPrincipal(principal));
    }

}
