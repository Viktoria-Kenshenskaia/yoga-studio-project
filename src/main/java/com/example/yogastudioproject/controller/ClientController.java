package com.example.yogastudioproject.controller;

import com.example.yogastudioproject.domain.model.Client;
import com.example.yogastudioproject.domain.payload.response.MessageResponse;
import com.example.yogastudioproject.domain.validation.ResponseErrorValidation;
import com.example.yogastudioproject.dto.ClientDto;
import com.example.yogastudioproject.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
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
    private final ResponseErrorValidation responseErrorValidation;


    @PostMapping("/create")
    public ResponseEntity<Object> createClient(@Valid @RequestBody ClientDto clientDto,
                                                  BindingResult bindingResult,
                                                  Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Client client = clientService.createClient(modelMapper.map(clientDto, Client.class), principal);
        return ResponseEntity.ok().body(modelMapper.map(client, ClientDto.class));
    }


    @GetMapping("/{clientId}/details")
    public ResponseEntity<ClientDto> getClientDetails(@PathVariable("clientId") Long clientId,
                                                      Principal principal) {
        Client client = clientService.getClientById(clientId, principal);
        return ResponseEntity.ok().body(modelMapper.map(client, ClientDto.class));
    }

    @PostMapping("/{clientId}/update")
    public ResponseEntity<Object> updateClient(@Valid @RequestBody ClientDto clientDto,
                                                  @PathVariable("clientId") Long clientId,
                                                  BindingResult bindingResult,
                                                  Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Client client = clientService.updateClient(modelMapper.map(clientDto, Client.class),clientId, principal);
        return ResponseEntity.ok().body(modelMapper.map(client, ClientDto.class));
    }

    @DeleteMapping("/{clientId}/delete")
    public ResponseEntity<Object> deleteClient(@PathVariable("clientId") Long clientId,
                                                        Principal principal) {
        clientService.deleteClient(clientId, principal);
        return ResponseEntity.ok(new MessageResponse("Client was deleted"));
    }


}
