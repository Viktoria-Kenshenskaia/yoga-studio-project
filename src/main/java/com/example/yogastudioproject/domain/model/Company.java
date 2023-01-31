package com.example.yogastudioproject.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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

    @NotEmpty(message = "Please enter company name")
    @Size(min = 1, max = 50, message = "Company name should be no less 1 and no more 50 signs")
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "created_date", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Set<AppUser> employees = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "company")
    private Set<Subscription> subscriptions = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Client> clients = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<OneClass> classes = new HashSet<>();

    @PrePersist
    private void createDate() {
        this.createdDate = LocalDateTime.now();
    }


}
