package com.example.yogastudioproject.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "contacts")
public class Contacts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contacts_id")
    private long contactsId;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "instagram")
    private String instagram;
    @Column(name = "telegram")
    private String telegram;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "company_id")
    private Company company;
}