package com.example.yogastudioproject.controller;

import com.example.yogastudioproject.domain.model.Client;
import com.example.yogastudioproject.dto.ClientDto;
import com.example.yogastudioproject.service.ClientService;
import lombok.RequiredArgsConstructor;
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
    private final ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<Client> createUser(@Valid @RequestBody ClientDto clientDTO,
                                             Principal principal) {
        return ResponseEntity.ok().body(clientService.createClient(clientDTO, principal));
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<Client> updateUser(@Valid @RequestBody ClientDto clientDto,
                                              @PathVariable("id") Long clientId) {
        return ResponseEntity.ok().body(clientService.updateClient(clientDto, clientId));
    }

    @DeleteMapping("/{id}/delete")
    public void deleteClient(@PathVariable("id") Long clientId) {
        clientService.deleteClient(clientId);
    }



}
