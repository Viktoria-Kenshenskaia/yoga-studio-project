package com.example.yogastudioproject.service;

import com.example.yogastudioproject.domain.model.Address;
import com.example.yogastudioproject.domain.model.AppUser;
import com.example.yogastudioproject.domain.model.Company;
import com.example.yogastudioproject.domain.model.Contacts;
import com.example.yogastudioproject.repository.AddressRepo;
import com.example.yogastudioproject.repository.AppUserRepo;
import com.example.yogastudioproject.repository.CompanyRepo;
import com.example.yogastudioproject.repository.ContactsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {
    private final CompanyRepo companyRepo;
    private final AppUserRepo appUserRepo;
    private final AddressRepo addressRepo;
    private final ContactsRepo contactsRepo;

    @Transactional
    public Company updateCompany(Company companyUpdate,
                                 Contacts contactsUpdate,
                                 Address addressUpdate,
                                 Principal principal) {

        Company company = getCompanyByPrincipal(principal);
        companyUpdate.setCompanyId(company.getCompanyId());
        contactsUpdate.setContactsId(company.getContacts().getContactsId());
        addressUpdate.setAddressId(company.getAddress().getAddressId());

        contactsRepo.save(contactsUpdate);
        addressRepo.save(addressUpdate);
        return companyRepo.save(companyUpdate);
    }
    @Transactional
    public void deleteCompany(Principal principal) {                                    //////
        Company company = getCompanyByPrincipal(principal);
        companyRepo.delete(company);
    }
    public Company getCompanyByPrincipal(Principal principal) {
        AppUser appUser = appUserRepo.findAppUserByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Company not found"));
        return appUser.getCompany();
    }

    public Company createCompany(String companyName) {
        Company company = new Company();
        Address address = new Address();
        Contacts contacts = new Contacts();

        address.setCompany(company);
        contacts.setCompany(company);

        company.setCompanyName(companyName);
        company.setAddress(address);
        company.setContacts(contacts);

        return company;
    }
}
