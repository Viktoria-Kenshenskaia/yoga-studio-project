package com.example.yogastudioproject.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class CompanyDto implements Serializable {
    private final String companyName;
    private final AddressDto address;
    private final ContactsDto contacts;
}
