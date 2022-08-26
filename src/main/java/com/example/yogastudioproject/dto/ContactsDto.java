package com.example.yogastudioproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ContactsDto implements Serializable {
    private  long contactsId;
    private  String phoneNumber;
    private  String instagram;
    private  String telegram;
}
