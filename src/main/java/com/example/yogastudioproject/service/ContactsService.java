package com.example.yogastudioproject.service;

import com.example.yogastudioproject.repository.ContactsRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactsService {
    private final ContactsRepo contactsRepo;
}
