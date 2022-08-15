package com.example.yogastudioproject.service;

import com.example.yogastudioproject.repository.AddressRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepo addressRepo;
}
