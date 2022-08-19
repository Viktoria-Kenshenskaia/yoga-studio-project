package com.example.yogastudioproject.repository;

import com.example.yogastudioproject.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {

    Optional<Client> findClientByEmail(String email);
    @Query("select clientId from Client where email = :email")
    Optional<Long> findIdByClientEmail(@Param("email") String email);

}
