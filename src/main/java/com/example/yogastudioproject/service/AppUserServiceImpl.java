package com.example.yogastudioproject.service;

import com.example.yogastudioproject.domain.model.AppUser;
import com.example.yogastudioproject.domain.model.Role;
import com.example.yogastudioproject.domain.payload.request.SignupRequest;
import com.example.yogastudioproject.repository.AppUserRepo;
import com.example.yogastudioproject.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepo appUserRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser createUser(AppUser appUser){
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepo.save(appUser);
    }

    public AppUser createUserFromSignupRequest(SignupRequest signupRequest) {
        AppUser appUser = new AppUser();
        appUser.setName(signupRequest.getName());
        appUser.setEmail(signupRequest.getEmail());
        appUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        appUser.getRoles().add(roleRepo.findRoleByName("ROLE_MANAGER"));


        return appUserRepo.save(appUser);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }
    @Override
    public List<AppUser> getAllUsers() {
        return appUserRepo.findAll();
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        AppUser appUser = getAppUserByEmail(email);
        Role role = roleRepo.findRoleByName(roleName);
        appUser.getRoles().add(role);
        appUserRepo.save(appUser);
    }

    @Override
    public AppUser getAppUserByEmail(String email) {
        return appUserRepo.findAppUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
