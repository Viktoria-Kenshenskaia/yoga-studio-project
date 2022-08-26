package com.example.yogastudioproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class CompanyDto implements Serializable {
    private long companyId;
    @NotEmpty(message = "Please enter company name")
    @Size(min = 1, max = 50, message = "Company name should be no less 1 and no more 50 signs")
    private String companyName;
    private AddressDto address;
    private ContactsDto contacts;
}
