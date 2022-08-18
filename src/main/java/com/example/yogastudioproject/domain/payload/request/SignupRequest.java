package com.example.yogastudioproject.domain.payload.request;

import com.example.yogastudioproject.domain.annotation.PasswordMatches;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@PasswordMatches
public class SignupRequest {
    @NotEmpty(message = "Please enter your name")
    private String firstname;

    @Email(message = "It should have email format")
    @NotBlank(message = "User email is required")
    private String email;
    @NotEmpty(message = "Password is required")
    @Size(min = 6)
    private String password;

    private String confirmPassword;
}
