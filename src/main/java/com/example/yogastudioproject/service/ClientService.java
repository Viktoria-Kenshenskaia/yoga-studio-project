package com.example.yogastudioproject.service;

import com.example.yogastudioproject.domain.model.Client;
import com.example.yogastudioproject.domain.model.Company;
import com.example.yogastudioproject.dto.ClientDto;
import com.example.yogastudioproject.repository.ClientRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepo clientRepo;
    private final ModelMapper modelMapper;
    private final CompanyService companyService;

    public Client createClient(ClientDto clientDTO, Principal principal) {
        Client client = clientDtoToClient(clientDTO);
        client.setCompany(companyService.getCompanyByPrincipal(principal));
        return clientRepo.save(client);
    }

    private Client clientDtoToClient(ClientDto clientDto){
        return modelMapper.map(clientDto, Client.class);
    }


    public Client updateClient(ClientDto clientDto, Long clientId) {
        Client client = modelMapper.map(clientDto, Client.class);
        client.setClientId(clientId);

        return clientRepo.save(client);
    }

    public void deleteClient(Long clientId) {
        Client client = clientRepo.findById(clientId).orElse(null);
        clientRepo.delete(client);
    }
}
