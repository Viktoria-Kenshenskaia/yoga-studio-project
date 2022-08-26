package com.example.yogastudioproject.controller;

import com.example.yogastudioproject.domain.model.Address;
import com.example.yogastudioproject.domain.model.Company;
import com.example.yogastudioproject.domain.model.Contacts;
import com.example.yogastudioproject.domain.payload.request.UpdateCompanyRequest;
import com.example.yogastudioproject.domain.payload.response.MessageResponse;
import com.example.yogastudioproject.domain.validation.ResponseErrorValidation;
import com.example.yogastudioproject.dto.CompanyDto;
import com.example.yogastudioproject.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final ModelMapper modelMapper;
    private final ResponseErrorValidation responseErrorValidation;


    @GetMapping("/details")
    public ResponseEntity<CompanyDto> detailsCompany(Principal principal) {
        Company company = companyService.getCompanyByPrincipal(principal);
        return ResponseEntity.ok().body(modelMapper.map(company, CompanyDto.class));
    }

    @PatchMapping("/update")
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<Object> updateCompany(@RequestBody UpdateCompanyRequest updateCompanyRequest,
                                                BindingResult bindingResult,
                                                Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Company company = companyService.updateCompany(modelMapper.map(updateCompanyRequest.getCompanyDto(), Company.class),
                modelMapper.map(updateCompanyRequest.getContactsDto(), Contacts.class),
                modelMapper.map(updateCompanyRequest.getAddressDto(), Address.class),
                principal);
        return ResponseEntity.ok().body(modelMapper.map(company, CompanyDto.class));
    }

    @DeleteMapping("/delete")
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<Object> deleteCompany(Principal principal) {
        companyService.deleteCompany(principal);
        return ResponseEntity.ok(new MessageResponse("Company was deleted"));
    }
}
