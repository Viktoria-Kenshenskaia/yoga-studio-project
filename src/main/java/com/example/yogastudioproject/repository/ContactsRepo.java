package com.example.yogastudioproject.repository;

import com.example.yogastudioproject.domain.model.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsRepo extends JpaRepository<Contacts, Long> {
}
