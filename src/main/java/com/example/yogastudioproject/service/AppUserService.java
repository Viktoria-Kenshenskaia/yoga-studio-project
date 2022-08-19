package com.example.yogastudioproject.service;

import com.example.yogastudioproject.domain.model.*;
import com.example.yogastudioproject.domain.payload.request.SignupRequest;
import com.example.yogastudioproject.domain.payload.request.SignupRequestCompany;
import com.example.yogastudioproject.dto.AppUserDto;
import com.example.yogastudioproject.dto.RoleDto;
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


    public AppUser createEmployee(AppUserDto appUserDto, Principal principal) {
        AppUser appUser = userDtoToUser(appUserDto);
        appUser.setPassword(passwordEncoder.encode(appUserDto.getPassword()));
        appUser.setCompany(companyService.getCompanyByPrincipal(principal));

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepo.findRoleByName("ROLE_MANAGER"));
        appUser.setRoles(roles);

        return appUserRepo.save(appUser);
    }



    public AppUser createUser(AppUser appUser) {                                    // FOR TEST
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        return appUserRepo.save(appUser);
    }

    private AppUser userDtoToUser(AppUserDto appUserDto) {
        return modelMapper.map(appUserDto, AppUser.class);
    }

    private AppUser createUserFromSignupRequest(SignupRequest signupRequest, Set<Role> roles) {
        AppUser appUser = new AppUser();
        appUser.setFirstname(signupRequest.getFirstname());
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
        Company company = companyService.createCompany(signupRequestCompany.getCompanyName());

        Contacts userContacts = new Contacts();
        userContacts.setCompany(company);



        Set<Role> roles = new HashSet<>();
        roles.add(roleRepo.findRoleByName("ROLE_ADMIN"));
        AppUser appUser = createUserFromSignupRequest(signupRequestCompany, roles);
        appUser.setContacts(userContacts);
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
