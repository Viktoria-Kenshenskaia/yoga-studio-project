package com.example.yogastudioproject.controller;

import com.example.yogastudioproject.dto.SubscriptionDto;
import com.example.yogastudioproject.service.ClientService;
import com.example.yogastudioproject.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
@RolesAllowed({"ROLE_ADMIN", "ROLE_MANAGER"})
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    private final ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<Object> createSubscription(@RequestBody SubscriptionDto subscriptionDto) {
        subscriptionService.createSubscription(subscriptionDto);
        return ResponseEntity.ok().body(null);
    }

@GetMapping
    public ResponseEntity<Object> getSubscriptionDetails() {

    }

}
