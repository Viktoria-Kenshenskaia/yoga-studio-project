package com.example.yogastudioproject.service;

import com.example.yogastudioproject.domain.exception.UserExistException;
import com.example.yogastudioproject.domain.model.AppUser;
import com.example.yogastudioproject.domain.model.Company;
import com.example.yogastudioproject.domain.model.Role;
import com.example.yogastudioproject.domain.payload.request.SignupRequest;
import com.example.yogastudioproject.domain.payload.request.SignupRequestCompany;
import com.example.yogastudioproject.domain.payload.request.SignupRequestEmployee;
import com.example.yogastudioproject.repository.AppUserRepo;
import com.example.yogastudioproject.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AppUserRepo appUserRepo;
    private final CompanyService companyService;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public AppUser createEmployee(SignupRequestEmployee signupRequestEmployee,
                                  Principal principal) {

        AppUser appUser = createUserFromSignupRequest(signupRequestEmployee, companyService.getCompanyByPrincipal(principal));

        appUser.setLastname(signupRequestEmployee.getLastname());
        appUser.setDateOfBirth(signupRequestEmployee.getDateOfBirth());

        return appUserRepo.save(appUser);
    }

    public AppUser createUserFromSignupRequestCompany(SignupRequestCompany signupRequestCompany) {

        Company company = companyService.createCompany(signupRequestCompany.getCompanyName());
        AppUser appUser = createUserFromSignupRequest(signupRequestCompany, company);

        return appUserRepo.save(appUser);
    }

    private AppUser createUserFromSignupRequest(SignupRequest signupRequest, Company company) {
        if (appUserRepo.findAppUserByEmail(signupRequest.getEmail()).isPresent()) {
            throw new UserExistException("An account with this email already exists.");
        }

        AppUser appUser = new AppUser();
        appUser.setCompany(company);
        appUser.setFirstname(signupRequest.getFirstname());
        appUser.setEmail(signupRequest.getEmail());
        appUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        signupRequest.getRoles().forEach(roleDto ->
                roles.add(roleRepo.findRoleByName(roleDto.getRoleName())));
        appUser.setRoles(roles);

        return appUser;
    }
}
