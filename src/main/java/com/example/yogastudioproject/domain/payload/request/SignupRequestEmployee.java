package com.example.yogastudioproject.domain.payload.request;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@AllArgsConstructor
@Data
public class SignupRequestEmployee extends SignupRequest{
    @Size(min = 1, max = 50, message = "Lastname should be no less 1 and no more 50 signs")
    private String lastname;
    private LocalDate dateOfBirth;
}
