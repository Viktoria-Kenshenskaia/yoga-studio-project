package com.example.yogastudioproject.controller;

import com.example.yogastudioproject.domain.model.Company;
import com.example.yogastudioproject.dto.CompanyDto;
import com.example.yogastudioproject.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping("/{companyId}/update")
    public ResponseEntity<Company> updateController(@RequestBody CompanyDto companyDto,
                                                    @PathVariable("companyId") Long companyId) {
        return ResponseEntity.ok().body(companyService.updateCompany(companyDto, companyId));
    }

}
