package com.example.yogastudioproject.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddressDto implements Serializable {
    private final long addressId;
    private final String country;
    private final String city;
    private final String address;
    private final String postCode;
}
