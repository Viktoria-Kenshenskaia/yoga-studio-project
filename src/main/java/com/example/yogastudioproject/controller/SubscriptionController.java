package com.example.yogastudioproject.controller;

import com.example.yogastudioproject.domain.model.Subscription;
import com.example.yogastudioproject.dto.SubscriptionDto;
import com.example.yogastudioproject.service.ClientService;
import com.example.yogastudioproject.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
@RolesAllowed({"ROLE_ADMIN", "ROLE_MANAGER"})
public class SubscriptionController {
    private final ModelMapper modelMapper;
    private final SubscriptionService subscriptionService;
    private final ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<Object> createSubscription(@RequestBody SubscriptionDto subscriptionDto) {
        subscriptionService.createSubscription(subscriptionDto);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/details")
    public ResponseEntity<SubscriptionDto> getSubscriptionDetails(@RequestBody Long subscriptionId) {
        Subscription subscription = subscriptionService.getSubscriptionById(subscriptionId);

        return ResponseEntity.ok().body(modelMapper.map(subscription, SubscriptionDto.class));
    }

}
