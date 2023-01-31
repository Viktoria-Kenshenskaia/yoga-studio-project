package com.example.yogastudioproject.service;

import com.example.yogastudioproject.domain.model.AppUser;
import com.example.yogastudioproject.domain.model.Company;
import com.example.yogastudioproject.repository.AppUserRepo;
import com.example.yogastudioproject.repository.CompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {
    private final CompanyRepo companyRepo;
    private final AppUserRepo appUserRepo;

    @Transactional
    public Company updateCompany(Company companyUpdate,
                                 Principal principal) {

        Company company = getCompanyByPrincipal(principal);
        companyUpdate.setCompanyId(company.getCompanyId());

        return companyRepo.save(companyUpdate);
    }
    @Transactional
    public void deleteCompany(Principal principal) {                                    //////
        Company company = getCompanyByPrincipal(principal);
        companyRepo.delete(company);
    }
    public Company getCompanyByPrincipal(Principal principal) {
        AppUser appUser = appUserRepo.findAppUserByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Company not found"));
        return appUser.getCompany();
    }

    public Company createCompany(String companyName) {
        Company company = new Company();
        company.setCompanyName(companyName);

        return company;
    }
}
