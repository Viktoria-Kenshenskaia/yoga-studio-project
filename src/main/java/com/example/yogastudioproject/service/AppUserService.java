package com.example.yogastudioproject.service;

import com.example.yogastudioproject.domain.model.AppUser;
import com.example.yogastudioproject.domain.model.Company;
import com.example.yogastudioproject.domain.model.Role;
import com.example.yogastudioproject.domain.payload.response.MessageResponse;
import com.example.yogastudioproject.repository.AppUserRepo;
import com.example.yogastudioproject.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppUserService {
    private final AppUserRepo appUserRepo;
    private final CompanyService companyService;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Transactional
    public AppUser createUser(AppUser appUser) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        return appUserRepo.save(appUser);
    }
    @Transactional
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    public List<AppUser> getAllEmployees(Principal principal) {
        Company company = companyService.getCompanyByPrincipal(principal);
        List<AppUser> appUsers = appUserRepo.findAllByCompany(company);
        appUsers.remove(getUserByPrincipal(principal));
        return appUsers;
    }

    @Transactional
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

    public AppUser getAppUserById(Long userId, Principal principal) {
        AppUser appUser = appUserRepo.findById(userId).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
        if (!isBelongCompany(appUser, principal))
            throw new RuntimeException();

            return appUser;

    }
    @Transactional
    public MessageResponse deleteUserById(Long userId, Principal principal) {
        AppUser appUser = appUserRepo.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!Objects.equals(getUserByPrincipal(principal).getUserId(), userId) &&
                isBelongCompany(appUser, principal)) {
            appUserRepo.delete(appUser);
            return new MessageResponse("User was deleted!");
        } else {
           return new MessageResponse("User cannot be deleted!");
        }
    }
    @Transactional
    public AppUser updateUser(AppUser appUserUpdate, Long userId, Principal principal) {                        ////////////
        AppUser appUserOld = appUserRepo.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!isBelongCompany(appUserOld, principal))
            throw new RuntimeException("This user cannot be updated!");

        appUserUpdate.setUserId(userId);
        appUserUpdate.setEmail(appUserOld.getEmail());
        appUserUpdate.setPassword(appUserOld.getPassword());
        appUserUpdate.setRoles(appUserOld.getRoles());

        return appUserRepo.save(appUserUpdate);
    }

    public AppUser getUserByPrincipal(Principal principal) {
        return appUserRepo.findAppUserByEmail(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public boolean isBelongCompany(AppUser appUser, Principal principal) {
        return appUser.getCompany().equals(companyService.getCompanyByPrincipal(principal));
    }

    public List<AppUser> getAllTeachers(Principal principal) {
        Role role = roleRepo.findRoleByName("ROLE_TEACHER");
        Company company = companyService.getCompanyByPrincipal(principal);
        List<AppUser> users = appUserRepo.findAllByCompany(company)
                .stream().filter(user -> user.getRoles().contains(role)).collect(Collectors.toList());

        return users;
    }
}
