package com.example.yogastudioproject.service;

import com.example.yogastudioproject.domain.model.Client;
import com.example.yogastudioproject.repository.ClientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepo clientRepo;
    private final CompanyService companyService;

    public Client createClient(Client client, Principal principal) {
        client.setCompany(companyService.getCompanyByPrincipal(principal));
        return clientRepo.save(client);
    }

    public Client updateClient(Client clientUpdate, Long clientId, Principal principal) {
        Client clientOld = getClientById(clientId, principal);

        clientUpdate.setClientId(clientOld.getClientId());

        return clientRepo.save(clientUpdate);
    }

    public void deleteClient(Long clientId, Principal principal) {
        Client client = getClientById(clientId, principal);
        clientRepo.delete(client);
    }

    public Client getClientById(Long clientId, Principal principal) {
        Client client = clientRepo.findById(clientId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!isBelongCompany(client, principal)) {
            throw new RuntimeException("Client not belong your company");
        }
        return client;

    }

    private boolean isBelongCompany(@NotNull Client client, Principal principal) {
        return companyService.getCompanyByPrincipal(principal).equals(client.getCompany());
    }
}
