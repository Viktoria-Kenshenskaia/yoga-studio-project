package com.example.yogastudioproject.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class ClientDto implements Serializable {
    @Email
    private final String email;
    @Size(min = 2, max = 30, message = "Firstname should not be less than 2 and not greater than 30 signs")
    private final String firstname;
    @Size(min = 2, max = 30, message = "Lastname should not be less than 2 and not greater than 30 signs")
    private final String lastname;
    private final LocalDate dateOfBirth;
    @NotNull
    private final String phoneNumber;
}
