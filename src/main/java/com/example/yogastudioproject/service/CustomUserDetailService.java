package com.example.yogastudioproject.service;

import com.example.yogastudioproject.domain.model.AppUser;
import com.example.yogastudioproject.repository.AppUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final AppUserRepo appUserRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepo.findAppUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return appUser;
    }

    public UserDetails loadUserById(Long userId) {
        AppUser appUser = appUserRepo.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return appUser;
    }
}
