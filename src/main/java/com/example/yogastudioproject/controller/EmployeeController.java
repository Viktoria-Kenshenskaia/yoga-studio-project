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
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class EmployeeController {
    private final AppUserService userService;
    private final ModelMapper modelMapper;
    private final ResponseErrorValidation responseErrorValidation;

    @GetMapping("/{userId}/details")
    public ResponseEntity<AppUserDto> detailsEmployee(@PathVariable("userId") Long userId, Principal principal) {
        AppUser appUser = userService.getAppUserById(userId, principal);
        return ResponseEntity.ok().body(modelMapper.map(appUser, AppUserDto.class));
    }

    @PatchMapping("/{userId}/update")
    public ResponseEntity<Object> updateEmployee(@Valid @RequestBody AppUserDto appUserDto,
                                                 @PathVariable("userId") Long userId,
                                                 BindingResult bindingResult,
                                                 Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        AppUser appUser = userService.updateUser(modelMapper.map(appUserDto, AppUser.class), userId, principal);
        return ResponseEntity.ok().body(modelMapper.map(appUser, AppUserDto.class));
    }

    @RolesAllowed({"ROLE_ADMIN"})
    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<Object> deleteEmployee(@PathVariable("userId") Long userId, Principal principal) {
        MessageResponse messageResponse = userService.deleteUserById(userId, principal);
        return ResponseEntity.ok(messageResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppUserDto>> getAllEmployees(Principal principal) {
        List<AppUser> appUsers = userService.getAllEmployees(principal);

        return ResponseEntity.ok().body(appUsers.stream()
                .map(user -> modelMapper.map(user, AppUserDto.class)).collect(Collectors.toList()));
    }

}
