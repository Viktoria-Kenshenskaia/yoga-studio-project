package com.example.yogastudioproject.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class SubscriptionDto implements Serializable {
    private  long subscriptionId;
    private  CompanyDto company;
    private  LocalDate openedAt;
    private  LocalDate finishedAt;
    @Size(min = 0, max = 1000, message = "Number of classes should not be less than 0 and not greater than 1000")
    private  int numberOfClasses;
    private  Set<OneClassDto> visitedClasses;
    @Min(value = 0)
    private  int price;
    private  ClientDto client;


}
