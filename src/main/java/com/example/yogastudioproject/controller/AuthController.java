package com.example.yogastudioproject.controller;

import com.example.yogastudioproject.domain.model.AppUser;
import com.example.yogastudioproject.domain.payload.request.LoginRequest;
import com.example.yogastudioproject.domain.payload.request.SignupRequestCompany;
import com.example.yogastudioproject.domain.payload.request.SignupRequestEmployee;
import com.example.yogastudioproject.domain.payload.response.MessageResponse;
import com.example.yogastudioproject.domain.payload.response.SuccessAuthenticationResponse;
import com.example.yogastudioproject.domain.validation.ResponseErrorValidation;
import com.example.yogastudioproject.dto.AppUserDto;
import com.example.yogastudioproject.security.JWTUtil;
import com.example.yogastudioproject.security.SecurityConstants;
import com.example.yogastudioproject.service.AppUserService;
import com.example.yogastudioproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JWTUtil jwtUtil;
    private final AppUserService userService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final ResponseErrorValidation responseErrorValidation;
    private final ModelMapper modelMapper;

    @PostMapping("/signup")
    public ResponseEntity<Object> registrationCompany(@Valid @RequestBody SignupRequestCompany signupRequestCompany,
                                                   BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        authService.createUserFromSignupRequestCompany(signupRequestCompany);

        return ResponseEntity.ok(new MessageResponse("Account registered successfully!"));
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping("/signup/employee")
    public ResponseEntity<Object> registrationEmployee(@Valid @RequestBody SignupRequestEmployee signupRequestEmployee,
                                                        Principal principal,
                                                        BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        authService.createEmployee(signupRequestEmployee, principal);

        return ResponseEntity.ok(new MessageResponse("Employee registered successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                                                   BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX + jwtUtil.generateToken(loginRequest.getEmail());
        AppUser appUser = userService.getAppUserByEmail(loginRequest.getEmail());
        SuccessAuthenticationResponse response = new SuccessAuthenticationResponse(jwt, modelMapper.map(appUser, AppUserDto.class));

        return ResponseEntity.ok().body(response);
    }

}
