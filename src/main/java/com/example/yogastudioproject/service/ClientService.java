package com.example.yogastudioproject.service;

import com.example.yogastudioproject.repository.ClientRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepo clientRepo;
}
