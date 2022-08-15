package com.example.yogastudioproject.service;

import com.example.yogastudioproject.domain.model.AppUser;
import com.example.yogastudioproject.domain.model.Company;
import com.example.yogastudioproject.domain.model.Role;
import com.example.yogastudioproject.domain.payload.request.SignupRequest;
import com.example.yogastudioproject.domain.payload.request.SignupRequestCompany;
import com.example.yogastudioproject.repository.AppUserRepo;
import com.example.yogastudioproject.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class AppUserServiceImpl {
    private final AppUserRepo appUserRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;


    public AppUser createUser(AppUser appUser){
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepo.save(appUser);
    }

//    public AppUser createUserFromSignupRequestAdmin(SignupRequest signupRequest) {
//        Set<Role> roles = new HashSet<>();
//        roles.add(roleRepo.findRoleByName("ROLE_ADMIN"));
//        AppUser appUser = createUserFromSignupRequest(signupRequest, roles);
//
//        return appUserRepo.save(appUser);
//    }

    private AppUser createUserFromSignupRequest(SignupRequest signupRequest, Set<Role> roles) {
        AppUser appUser = new AppUser();
        appUser.setFirstname(signupRequest.getName());
        appUser.setEmail(signupRequest.getEmail());
        appUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        roles.forEach(role -> appUser.getRoles().add(role));

        return appUser;
    }

    public AppUser createEmployeeFromSignupRequest(SignupRequest signupRequest) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepo.findRoleByName("ROLE_MANAGER"));
        AppUser appUser = createUserFromSignupRequest(signupRequest, roles);

        return appUserRepo.save(appUser);
    }

    public AppUser createUserFromSignupRequestCompany(SignupRequestCompany signupRequestCompany) {
        Company company = new Company();
        company.setCompanyName(signupRequestCompany.getCompanyName());
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepo.findRoleByName("ROLE_ADMIN"));
        AppUser appUser = createUserFromSignupRequest(signupRequestCompany, roles);
        appUser.setCompany(company);

        return appUserRepo.save(appUser);
    }


    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    public List<AppUser> getAllUsers() {
        return appUserRepo.findAll();
    }


    public void addRoleToUser(String email, String roleName) {
        AppUser appUser = getAppUserByEmail(email);
        Role role = roleRepo.findRoleByName(roleName);
        appUser.getRoles().add(role);
        appUserRepo.save(appUser);
    }


    public AppUser getAppUserByEmail(String email) {
        return appUserRepo.findAppUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
