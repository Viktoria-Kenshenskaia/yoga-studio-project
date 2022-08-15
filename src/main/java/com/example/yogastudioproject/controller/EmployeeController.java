package com.example.yogastudioproject.controller;

import com.example.yogastudioproject.domain.model.AppUser;
import com.example.yogastudioproject.domain.payload.request.SignupRequest;
import com.example.yogastudioproject.dto.AppUserDto;
import com.example.yogastudioproject.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class EmployeeController {
    private final AppUserService userService;

    @PostMapping("/create")
    public ResponseEntity<AppUser> registrationEmployee(@Valid @RequestBody SignupRequest signupRequest) {
        AppUser appUser = userService.createEmployeeFromSignupRequest(signupRequest);
        return ResponseEntity.ok().body(appUser);
    }
    @GetMapping("/all")
    public ResponseEntity<List<AppUser>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

//    @PatchMapping("/update")
//    public ResponseEntity<AppUser> updateUser(@Valid @RequestBody AppUserDto appUserDto) {
//        return ResponseEntity.ok().body();
//    }

}
