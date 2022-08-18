package com.example.yogastudioproject.controller;

import com.example.yogastudioproject.domain.model.Company;
import com.example.yogastudioproject.dto.AppUserDto;
import com.example.yogastudioproject.dto.CompanyDto;
import com.example.yogastudioproject.service.AppUserService;
import com.example.yogastudioproject.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    private final AppUserService userService;

    @PostMapping("/{companyId}/update")
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<Company> updateCompany(@RequestBody CompanyDto companyDto,
                                                    @PathVariable("companyId") Long companyId) {
        return ResponseEntity.ok().body(companyService.updateCompany(companyDto, companyId));
    }

    @DeleteMapping("/delete")
    @RolesAllowed({"ROLE_ADMIN"})
    public void deleteCompany(Principal principal) {
        companyService.deleteCompany(principal);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<AppUserDto>> getAllEmployee(Principal principal) {
        List<AppUserDto> employees = userService.getAllEmployee(principal);
        return ResponseEntity.ok().body(employees);
    }

}
