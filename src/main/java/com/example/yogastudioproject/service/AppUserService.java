package com.example.yogastudioproject.service;


import com.example.yogastudioproject.domain.model.AppUser;
import com.example.yogastudioproject.domain.model.Role;
import com.example.yogastudioproject.domain.payload.request.SignupRequest;

import java.util.List;

public interface AppUserService {
    AppUser createUser(AppUser appUser);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    AppUser getAppUserByEmail(String email);
    List<AppUser> getAllUsers();

//    AppUser createUserFromSignupRequest(SignupRequest signupRequest);

    AppUser createEmployeeFromSignupRequest(SignupRequest signupRequest);
}
