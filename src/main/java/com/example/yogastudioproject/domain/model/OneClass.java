package com.example.yogastudioproject.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "one_class")
public class OneClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private long classId;

    @Column(name = "date_of_class")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateOfClass;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_user_id")
    private AppUser teacher;
    @ManyToOne
    @JoinColumn(name = "company_id", updatable = false)
    private Company company;

    @ManyToMany
    @JoinTable(
            name = "subscription_class",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "subscription_id")
    )
    private List<Subscription> subscription;

}