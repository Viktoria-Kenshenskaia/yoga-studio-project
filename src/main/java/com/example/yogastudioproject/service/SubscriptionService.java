package com.example.yogastudioproject.service;

import com.example.yogastudioproject.repository.SubscriptionRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepo subscriptionRepo;
}
