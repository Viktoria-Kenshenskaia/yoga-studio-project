package com.example.yogastudioproject.domain.payload.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassToSubscription {
    private Long subscriptionId;
    private Long classId;
}
