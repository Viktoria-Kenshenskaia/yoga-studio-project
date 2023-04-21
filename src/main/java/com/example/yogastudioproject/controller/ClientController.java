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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;

/**
 * This class represents the REST controller for managing clients in the yoga studio system.
 * <p>
 * It handles HTTP requests related to creating, retrieving, updating, and deleting clients.
 * <p>
 * Only users with roles "ROLE_ADMIN" and "ROLE_MANAGER" are allowed to access the endpoints in this controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
@RolesAllowed({"ROLE_ADMIN", "ROLE_MANAGER"})
public class ClientController {

    /**
     * The model mapper to convert between DTOs and entities.
     */
    private final ModelMapper modelMapper;
    /**
     * The service to manage clients.
     */
    private final ClientService clientService;
    /**
     * The service to validate and handle validation errors.
     */
    private final ResponseErrorValidation responseErrorValidation;

    /**
     * Creates a new client in the yoga studio system.
     *
     * @param clientDto     The DTO containing the client's information to be created.
     * @param bindingResult The result of the validation of the clientDto object.
     * @param principal     The authenticated principal object.
     * @return A ResponseEntity object containing the created ClientDto object in the response body, or an error message.
     */
    @PostMapping("/create")
    public ResponseEntity<Object> createClient(@Valid @RequestBody ClientDto clientDto,
                                               BindingResult bindingResult,
                                               Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Client client = clientService.createClient(modelMapper.map(clientDto, Client.class), principal);
        return ResponseEntity.ok().body(modelMapper.map(client, ClientDto.class));
    }

    /**
     * Retrieves the details of a client with a given ID from the yoga studio system.
     *
     * @param clientId  The ID of the client to be retrieved.
     * @param principal The authenticated principal object.
     * @return A ResponseEntity object containing the retrieved ClientDto object in the response body, or an error message.
     */
    @GetMapping("/{clientId}/details")
    public ResponseEntity<ClientDto> getClientDetails(@PathVariable("clientId") Long clientId,
                                                      Principal principal) {
        Client client = clientService.getClientById(clientId, principal);
        return ResponseEntity.ok().body(modelMapper.map(client, ClientDto.class));
    }

    /**
     * Updates the information of an existing client in the yoga studio system.
     *
     * @param clientDto     The DTO containing the client's updated information.
     * @param clientId      The ID of the client to be updated.
     * @param bindingResult The result of the validation of the clientDto object.
     * @param principal     The authenticated principal object.
     * @return A ResponseEntity object containing the updated ClientDto object in the response body, or an error message.
     */
    @PutMapping("/{clientId}")
    public ResponseEntity<Object> updateClient(@Valid @RequestBody ClientDto clientDto,
                                               @PathVariable("clientId") Long clientId,
                                               BindingResult bindingResult,
                                               Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Client client = clientService.updateClient(modelMapper.map(clientDto, Client.class), clientId, principal);
        return ResponseEntity.ok().body(modelMapper.map(client, ClientDto.class));
    }

    /**
     * Deletes a client with a given ID from the yoga studio system.
     *
     * @param clientId  The ID of the client to be deleted.
     * @param principal The authenticated principal object.
     * @return A ResponseEntity object containing a success message in the response body.
     */
    @DeleteMapping("/{clientId}")
    public ResponseEntity<Object> deleteClient(@PathVariable("clientId") Long clientId,
                                               Principal principal) {
        clientService.deleteClient(clientId, principal);
        return ResponseEntity.ok(new MessageResponse("Client was deleted"));
    }
}
