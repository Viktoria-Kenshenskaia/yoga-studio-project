package com.example.yogastudioproject.domain.payload.request;

import com.example.yogastudioproject.domain.annotation.PasswordMatches;
import com.example.yogastudioproject.dto.RoleDto;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Set;

@Data
@PasswordMatches
public class SignupRequest {
    @NotEmpty(message = "Please enter your firstname")
    @Size(min = 1, max = 50, message = "Firstname should be no less 1 and no more 50 signs")
    private String firstname;

    @Email(message = "It should have email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 6, max = 30, message = "Password should be no less 6 and no more 30 signs")
    private String password;

    private String confirmPassword;

    private Set<RoleDto> roles;
}
