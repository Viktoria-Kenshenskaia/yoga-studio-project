package com.example.yogastudioproject.dto;

import com.example.yogastudioproject.domain.model.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Data
public class AppUserDto implements Serializable {
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    @Email
    private String email;
    private Set<RoleDto> roles;
}
