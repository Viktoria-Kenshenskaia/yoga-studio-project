package com.example.yogastudioproject.controller;

import com.example.yogastudioproject.service.AddressService;
import com.example.yogastudioproject.service.ContactsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactsController {
    private final AddressService addressService;
    private final ContactsService contactsService;







}
