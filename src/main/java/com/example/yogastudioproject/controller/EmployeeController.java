package com.example.yogastudioproject.controller;

import com.example.yogastudioproject.domain.model.AppUser;
import com.example.yogastudioproject.domain.payload.response.MessageResponse;
import com.example.yogastudioproject.domain.validation.ResponseErrorValidation;
import com.example.yogastudioproject.dto.AppUserDto;
import com.example.yogastudioproject.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class handles requests related to employees and teachers.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class EmployeeController {

    /**
     * Service for managing application users.
     */
    private final AppUserService userService;

    /**
     * ModelMapper for mapping DTOs to entities and vice versa.
     */
    private final ModelMapper modelMapper;

    /**
     * Service for handling validation errors.
     */
    private final ResponseErrorValidation responseErrorValidation;

    /**
     * Returns details of a specific employee.
     *
     * @param userId    the ID of the employee to retrieve
     * @param principal the authenticated principal
     * @return a ResponseEntity containing the details of the requested employee
     */
    @GetMapping("/{userId}")
    public ResponseEntity<AppUserDto> detailsEmployee(@PathVariable("userId") Long userId, Principal principal) {
        AppUser appUser = userService.getAppUserById(userId, principal);
        return ResponseEntity.ok().body(modelMapper.map(appUser, AppUserDto.class));
    }

    /**
     * Updates the details of a specific employee.
     *
     * @param appUserDto    the updated details of the employee
     * @param userId        the ID of the employee to update
     * @param bindingResult the result of validating the updated details
     * @param principal     the authenticated principal
     * @return a ResponseEntity containing the updated details of the employee
     */
    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateEmployee(@Valid @RequestBody AppUserDto appUserDto,
                                                 @PathVariable("userId") Long userId,
                                                 BindingResult bindingResult,
                                                 Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        AppUser appUser = userService.updateUser(modelMapper.map(appUserDto, AppUser.class), userId, principal);
        return ResponseEntity.ok().body(modelMapper.map(appUser, AppUserDto.class));
    }

    /**
     * Deletes a specific employee.
     *
     * @param userId    the ID of the employee to delete
     * @param principal the authenticated principal
     * @return a ResponseEntity containing a message indicating that the employee was deleted
     */
    @RolesAllowed({"ROLE_ADMIN"})
    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable("userId") Long userId, Principal principal) {
        MessageResponse messageResponse = userService.deleteUserById(userId, principal);
        return ResponseEntity.ok(messageResponse);
    }

    /**
     * Returns details of all employees.
     *
     * @param principal the authenticated principal
     * @return a ResponseEntity containing a list of all employees and their details
     */
    @GetMapping("/all")
    public ResponseEntity<List<AppUserDto>> getAllEmployees(Principal principal) {
        List<AppUser> appUsers = userService.getAllEmployees(principal);

        return ResponseEntity.ok().body(appUsers.stream()
                .map(user -> modelMapper.map(user, AppUserDto.class)).collect(Collectors.toList()));
    }

    /**
     * Returns details of all teachers.
     *
     * @param principal the authenticated principal
     * @return a ResponseEntity containing a list of all teachers and their details
     */
    @GetMapping("/teachers")
    public ResponseEntity<List<AppUserDto>> getAllTeachers(Principal principal) {
        List<AppUser> teachers = userService.getAllTeachers(principal);

        return ResponseEntity.ok().body(teachers.stream()
                .map(user -> modelMapper.map(user, AppUserDto.class)).collect(Collectors.toList()));
    }
}
