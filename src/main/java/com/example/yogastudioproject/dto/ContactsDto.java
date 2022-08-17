package com.example.yogastudioproject.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ContactsDto implements Serializable {
    private final long contactsId;
    private final String email;
    private final String phoneNumber;
    private final String instagram;
    private final String telegram;
}
