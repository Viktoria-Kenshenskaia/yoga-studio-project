package com.example.yogastudioproject.service;

import com.example.yogastudioproject.domain.model.Company;
import com.example.yogastudioproject.repository.CompanyRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepo companyRepo;

    public Company updateCompany(Company companyUpdate, Long companyId) {
        companyUpdate.setCompanyId(companyId);
        return companyRepo.save(companyUpdate);
    }
}
