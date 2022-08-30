package com.example.yogastudioproject.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private long subscriptionId;
    @ManyToOne
    @JoinColumn(name = "company_company_id", updatable = false)
    private Company company;

    @Column(name = "opened_at")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate openedAt;


    @Column(name = "finished_at")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate finishedAt;

    @Column(name = "number_of_classes")
//    @Size(min = 0, max = 1000, message = "Number of classes should not be less than 0 and not greater than 1000")
    @Min(0)
    @Max(1000)
    private Integer numberOfClasses;

    @ManyToMany(mappedBy = "subscription")
    private List<OneClass> visitedClasses;

    @Column(name = "price")
    @Min(value = 0)
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "client_id", updatable = false)
    private Client client;

//    public int getNumberOfClasses() {
//        return numberOfClasses - visitedClasses.size();
//    }


}