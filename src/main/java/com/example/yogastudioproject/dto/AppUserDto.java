package com.example.yogastudioproject.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class AppUserDto implements Serializable {
    private final Long userId;
    private final String firstname;
    private final String lastname;
    private final LocalDate dateOfBirth;
    @Email
    private final String email;
    private final String password;
}
