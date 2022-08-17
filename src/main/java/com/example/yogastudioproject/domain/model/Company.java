package com.example.yogastudioproject.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private long companyId;

    @Column(name = "company_name")
    private String companyName;
    @Column(name = "created_date", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "contacts_id")
    private Contacts contacts;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<AppUser> employees = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "company")
    private Set<Subscription> subscriptions = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Client> clients = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY)
    private Set<OneClass> classes = new HashSet<>();



    @PrePersist
    private void createDate() {
        this.createdDate = LocalDateTime.now();
        this.contacts = new Contacts();
        this.address = new Address();
    }



}
