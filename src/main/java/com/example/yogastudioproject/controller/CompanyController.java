package com.example.yogastudioproject.controller;

import com.example.yogastudioproject.domain.model.Company;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

/**
 * This class represents a REST controller for managing company-related requests.
 */
@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    /**
     * The service for managing companies.
     */
    private final CompanyService companyService;
    /**
     * The mapper for converting between objects.
     */
    private final ModelMapper modelMapper;
    /**
     * The validator for handling validation errors.
     */
    private final ResponseErrorValidation responseErrorValidation;

    /**
     * Retrieves details for the currently authenticated company.
     *
     * @param principal the authenticated principal
     * @return a ResponseEntity containing the details of the company as a CompanyDto
     */
    @GetMapping("/details")
    public ResponseEntity<CompanyDto> detailsCompany(Principal principal) {
        Company company = companyService.getCompanyByPrincipal(principal);
        return ResponseEntity.ok().body(modelMapper.map(company, CompanyDto.class));
    }

    /**
     * Updates the details for the currently authenticated company.
     *
     * @param updateCompanyRequest the request containing the updated details
     * @param bindingResult        the result of the validation process
     * @param principal            the authenticated principal
     * @return a ResponseEntity containing the updated details of the company as a CompanyDto
     */
    @PutMapping
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<Object> updateCompany(@RequestBody UpdateCompanyRequest updateCompanyRequest,
                                                BindingResult bindingResult,
                                                Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Company company = companyService.updateCompany(modelMapper.map(updateCompanyRequest.getCompanyDto(), Company.class),
                principal);
        return ResponseEntity.ok().body(modelMapper.map(company, CompanyDto.class));
    }

    /**
     * Deletes the currently authenticated company.
     *
     * @param principal the authenticated principal
     * @return a ResponseEntity containing a message indicating that the company was deleted
     */
    @DeleteMapping
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<Object> deleteCompany(Principal principal) {
        companyService.deleteCompany(principal);
        return ResponseEntity.ok(new MessageResponse("Company was deleted"));
    }
}
