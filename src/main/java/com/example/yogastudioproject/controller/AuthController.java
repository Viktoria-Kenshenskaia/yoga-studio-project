package com.example.yogastudioproject.controller;

import com.example.yogastudioproject.domain.model.AppUser;
import com.example.yogastudioproject.domain.model.Role;
import com.example.yogastudioproject.domain.payload.request.LoginRequest;
import com.example.yogastudioproject.domain.payload.request.SignupRequest;
import com.example.yogastudioproject.domain.payload.response.JWTSuccessResponse;
import com.example.yogastudioproject.security.JWTTokenProvider;
import com.example.yogastudioproject.security.SecurityConstants;
import com.example.yogastudioproject.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class AuthController {
    private final JWTTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final AppUserService userService;



    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTSuccessResponse(true, jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registrationUser(@Valid @RequestBody SignupRequest signupRequest) {
        AppUser appUser = userService.createUserFromSignupRequest(signupRequest);

        return ResponseEntity.ok().body(appUser);
    }




}
