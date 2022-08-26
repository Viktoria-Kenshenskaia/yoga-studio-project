package com.example.yogastudioproject.controller;

import com.example.yogastudioproject.domain.model.Subscription;
import com.example.yogastudioproject.domain.payload.response.MessageResponse;
import com.example.yogastudioproject.domain.validation.ResponseErrorValidation;
import com.example.yogastudioproject.dto.SubscriptionDto;
import com.example.yogastudioproject.service.ClientService;
import com.example.yogastudioproject.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
@RolesAllowed({"ROLE_ADMIN", "ROLE_MANAGER"})
public class SubscriptionController {
    private final ModelMapper modelMapper;
    private final SubscriptionService subscriptionService;
    private final ResponseErrorValidation responseErrorValidation;

    @PostMapping("/{clientId}/create")
    public ResponseEntity<Object> createSubscription(@Valid @RequestBody SubscriptionDto subscriptionDto,
                                                     @PathVariable("clientId") Long clientId,
                                                     BindingResult bindingResult,
                                                     Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Subscription subscription =
                subscriptionService.createSubscription(modelMapper.map(subscriptionDto, Subscription.class), clientId, principal);
        return ResponseEntity.ok().body(modelMapper.map(subscription, SubscriptionDto.class));
    }

    @GetMapping("/{subscriptionId}/details")
    public ResponseEntity<SubscriptionDto> getSubscriptionDetails(@PathVariable("subscriptionId") Long subscriptionId,
                                                                  Principal principal) {
        Subscription subscription = subscriptionService.getSubscriptionById(subscriptionId, principal);

        return ResponseEntity.ok().body(modelMapper.map(subscription, SubscriptionDto.class));
    }

    @DeleteMapping("/{subscriptionId}/details")
    public ResponseEntity<Object> deleteSubscription(@PathVariable("subscriptionId") Long subscriptionId,
                                                     Principal principal) {
        subscriptionService.deleteSubscription(subscriptionId, principal);
        return ResponseEntity.ok(new MessageResponse("Subscription was deleted!"));
    }
}
