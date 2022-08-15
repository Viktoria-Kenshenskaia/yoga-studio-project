package com.example.yogastudioproject.domain.payload.request;

import com.example.yogastudioproject.domain.annotation.PasswordMatches;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
@Data
@PasswordMatches
public class SignupRequestCompany extends SignupRequest{

//        @NotEmpty(message = "Please enter your name")
//        private String name;
        @NotEmpty(message = "Please enter company name")
        private String companyName;

//        @Email(message = "It should have email format")
//        @NotBlank(message = "User email is required")
//        private String email;
//        @NotEmpty(message = "Password is required")
//        @Size(min = 6)
//        private String password;
//
//        private String confirmPassword;

}
