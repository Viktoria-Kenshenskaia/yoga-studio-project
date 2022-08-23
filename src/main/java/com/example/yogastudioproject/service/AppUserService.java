package com.example.yogastudioproject.service;

import com.example.yogastudioproject.domain.model.AppUser;
import com.example.yogastudioproject.domain.model.Company;
import com.example.yogastudioproject.domain.model.Contacts;
import com.example.yogastudioproject.domain.model.Role;
import com.example.yogastudioproject.domain.payload.request.SignupRequestEmployee;
import com.example.yogastudioproject.domain.payload.request.SignupRequest;
import com.example.yogastudioproject.domain.payload.request.SignupRequestCompany;
import com.example.yogastudioproject.dto.AppUserDto;
import com.example.yogastudioproject.repository.AppUserRepo;
import com.example.yogastudioproject.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AppUserService {
    private final AppUserRepo appUserRepo;
    private final CompanyService companyService;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;


//    public AppUser createEmployee(SignupRequestEmployee signupRequestEmployee,
//                                  Principal principal) {
//
//        AppUser appUser = createUserFromSignupRequest(signupRequestEmployee, companyService.getCompanyByPrincipal(principal));
//
//        appUser.setLastname(signupRequestEmployee.getLastname());
//        appUser.setDateOfBirth(signupRequestEmployee.getDateOfBirth());
//
//        return appUserRepo.save(appUser);
//    }




    public AppUser createUser(AppUser appUser) {                                    // FOR TEST
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        return appUserRepo.save(appUser);
    }

//    private AppUser createUserFromSignupRequest(SignupRequest signupRequest, Company company) {
//        if (appUserRepo.findAppUserByEmail(signupRequest.getEmail()).isPresent()) {
//            throw new RuntimeException("An account with this email already exists.");
//        }
//
//        AppUser appUser = new AppUser();
//        appUser.setCompany(company);
//        appUser.setFirstname(signupRequest.getFirstname());
//        appUser.setEmail(signupRequest.getEmail());
//        appUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
//
//        Contacts userContacts = new Contacts();
//        userContacts.setCompany(company);
//        appUser.setContacts(userContacts);
//
//        Set<Role> roles = new HashSet<>();
//        signupRequest.getRoles().forEach(roleDto ->
//                roles.add(roleRepo.findRoleByName(roleDto.getRoleName())));
//        appUser.setRoles(roles);
//
//        return appUser;
//    }

//    public AppUser createUserFromSignupRequestCompany(SignupRequestCompany signupRequestCompany) {
//
//        Company company = companyService.createCompany(signupRequestCompany.getCompanyName());
//        AppUser appUser = createUserFromSignupRequest(signupRequestCompany, company);
//
//        return appUserRepo.save(appUser);
//    }


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

    public List<AppUserDto> getAllEmployee(Principal principal) {
        Company company = companyService.getCompanyByPrincipal(principal);
        List<AppUserDto> users = appUserRepo.findAllByCompany(company)
                .stream().map(user -> modelMapper.map(user, AppUserDto.class))
                .collect(Collectors.toList());


        return users;
    }

    public AppUser getAppUserById(Long userId) {
        return appUserRepo.findById(userId).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
    }

    public void deleteUserById(Long userId) {
        AppUser appUser = appUserRepo.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        appUserRepo.delete(appUser);
    }

    public AppUserDto updateUser(AppUserDto appUserDto, Long userId) {
        AppUser appUser = modelMapper.map(appUserDto, AppUser.class);
        appUser.setUserId(userId);
        appUserRepo.save(appUser);
        return modelMapper.map(appUser, AppUserDto.class);
    }
}
