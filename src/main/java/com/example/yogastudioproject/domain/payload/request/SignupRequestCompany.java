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

        @NotEmpty(message = "Please enter company name")
        @Size(min = 1, max = 50, message = "Company name should be no less 1 and no more 50 signs")
        private String companyName;

}
