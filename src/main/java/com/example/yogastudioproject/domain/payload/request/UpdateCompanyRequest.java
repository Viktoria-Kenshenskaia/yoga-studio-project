package com.example.yogastudioproject.domain.payload.request;

import com.example.yogastudioproject.dto.AddressDto;
import com.example.yogastudioproject.dto.CompanyDto;
import com.example.yogastudioproject.dto.ContactsDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCompanyRequest {
    private CompanyDto companyDto;
    private ContactsDto contactsDto;
    private AddressDto addressDto;
}
