package com.example.yogastudioproject.controller;

import com.example.yogastudioproject.domain.model.Client;
import com.example.yogastudioproject.dto.ClientDto;
import com.example.yogastudioproject.service.ClientService;
import com.example.yogastudioproject.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client")
@RolesAllowed({"ROLE_ADMIN", "ROLE_MANAGER"})
public class ClientController {
    private final ModelMapper modelMapper;
    private final ClientService clientService;


    @PostMapping("/create")
    public ResponseEntity<ClientDto> createClient(@Valid @RequestBody ClientDto clientDto,
                                                  Principal principal) {
        Client client = clientService.createClient(modelMapper.map(clientDto, Client.class), principal);
        return ResponseEntity.ok().body(modelMapper.map(client, ClientDto.class));
    }


    @GetMapping("/details")
    public ResponseEntity<ClientDto> getClientDetails(@RequestBody Long clientId,
                                                      Principal principal) {
        Client client = clientService.getClient(clientId, principal);

        return ResponseEntity.ok().body(modelMapper.map(client, ClientDto.class));
    }

    @PostMapping("/update")
    public ResponseEntity<ClientDto> updateClient(@Valid @RequestBody ClientDto clientDto,
                                                  Principal principal) {
        Client client = clientService.updateClient(modelMapper.map(clientDto, Client.class), principal);
        return ResponseEntity.ok().body(modelMapper.map(client, ClientDto.class));
    }

    @DeleteMapping("/delete")
    public void deleteClient(@RequestBody ClientDto clientDto,
                             Principal principal) {
        Client client = modelMapper.map(clientDto, Client.class);
        clientService.deleteClient(client, principal);
    }


}
