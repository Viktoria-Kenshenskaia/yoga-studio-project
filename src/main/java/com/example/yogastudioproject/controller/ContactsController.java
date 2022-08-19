package com.example.yogastudioproject.controller;

import com.example.yogastudioproject.dto.ContactsDto;
import com.example.yogastudioproject.service.AddressService;
import com.example.yogastudioproject.service.ContactsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactsController {

    private final ContactsService contactsService;
    private  final ModelMapper modelMapper;

//    @PostMapping("/update")
//    public ResponseEntity<ContactsDto> updateContacts(@RequestBody ContactsDto contactsDto) {
//
//    }


}
