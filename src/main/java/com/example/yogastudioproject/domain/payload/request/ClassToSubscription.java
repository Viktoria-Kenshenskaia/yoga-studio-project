package com.example.yogastudioproject.domain.payload.request;

import lombok.Data;

@Data
public class ClassToSubscription {
    private Long subscriptionId;
    private Long classId;
}
