package com.example.yogastudioproject.repository;

import com.example.yogastudioproject.domain.model.AppUser;
import com.example.yogastudioproject.domain.model.Company;
import com.example.yogastudioproject.domain.model.OneClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OneClassRepo extends JpaRepository<OneClass, Long> {

    List<OneClass> findAllByCompany(Company company);
    List<OneClass> findAllByTeacher(AppUser teacher);

    List<OneClass> findAllByCompanyAndDateOfClassBeforeAndDateOfClassAfter(Company company, LocalDateTime dateOfClass, LocalDateTime dateOfClass2);
}
