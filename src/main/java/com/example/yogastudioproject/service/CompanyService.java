package com.example.yogastudioproject.service;

import com.example.yogastudioproject.domain.model.AppUser;
import com.example.yogastudioproject.domain.model.Company;
import com.example.yogastudioproject.dto.CompanyDto;
import com.example.yogastudioproject.repository.AppUserRepo;
import com.example.yogastudioproject.repository.CompanyRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepo companyRepo;
    private final AppUserRepo appUserRepo;

    public Company updateCompany(Company companyUpdate, Principal principal) {
        Company company = getCompanyByPrincipal(principal);
        companyUpdate.setCompanyId(company.getCompanyId());
        return companyRepo.save(companyUpdate);
    }

    public void deleteCompany(Principal principal) {
        companyRepo.delete(getCompanyByPrincipal(principal));
    }
    public Company getCompanyByPrincipal(Principal principal) {
        AppUser appUser = appUserRepo.findAppUserByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Company not found"));
        return appUser.getCompany();
    }
}
