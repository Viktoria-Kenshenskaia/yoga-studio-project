package com.example.yogastudioproject.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private long clientId;

    @Email
    @Column(name = "email", unique = true)
    @UniqueElements
    private String email;

    @Column(name = "firstname")
    @Size(min = 2, max = 30, message = "Firstname should not be less than 2 and not greater than 30 signs")
    private String firstname;

    @Column(name = "lastname")
    @Size(min = 2, max = 30, message = "Lastname should not be less than 2 and not greater than 30 signs")
    private String lastname;

    @Column(name = "date_of_birth")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Column(name = "created_date", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;


    @NotNull
    @Column(name = "phone_number")
//    @PhoneNumber
    private String phoneNumber;
    @OneToMany(mappedBy = "client")
    private Set<Subscription> subscriptions;

    @ManyToOne
    @JoinColumn(name = "studio_id")
    private Company company;

    @PrePersist
    private void createdDate() {
        this.createdDate = LocalDateTime.now();
    }



}
