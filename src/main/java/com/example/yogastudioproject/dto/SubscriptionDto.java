package com.example.yogastudioproject.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class SubscriptionDto implements Serializable {
    private  Long subscriptionId;
//    private  CompanyDto company;
    private  LocalDate openedAt;
    private  LocalDate finishedAt;
//    @Size(min = 1, max = 1000, message = "Number of classes should not be less than 0 and not greater than 1000")
    @Min(0)
    @Max(1000)
    private  Integer numberOfClasses;
    private  Set<OneClassDto> visitedClasses;
    @Min(0)
    private  Integer price;
    private  ClientDto client;


}
