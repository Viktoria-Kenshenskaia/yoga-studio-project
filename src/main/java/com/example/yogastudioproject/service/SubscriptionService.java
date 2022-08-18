package com.example.yogastudioproject.service;

import com.example.yogastudioproject.domain.model.Subscription;
import com.example.yogastudioproject.dto.SubscriptionDto;
import com.example.yogastudioproject.repository.SubscriptionRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepo subscriptionRepo;
    private final ModelMapper modelMapper;


    public Subscription getSubscriptionById(Long subscriptionId) {
        return subscriptionRepo.findById(subscriptionId).orElseThrow(() -> new RuntimeException("Sub not found"));
    }

    public SubscriptionDto createSubscription(SubscriptionDto subscriptionDto) {
        Subscription subscription = modelMapper.map(subscriptionDto, Subscription.class);
        subscriptionRepo.save(subscription);
        return null;
    }

    public boolean isActive(Subscription subscription) {                                                       // Need check time format
        LocalDate now = LocalDate.now(Clock.systemDefaultZone());
        if (now.isAfter(subscription.getOpenedAt()) &&
                now.isBefore(subscription.getFinishedAt()) &&
                subscription.getNumberOfClasses() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
