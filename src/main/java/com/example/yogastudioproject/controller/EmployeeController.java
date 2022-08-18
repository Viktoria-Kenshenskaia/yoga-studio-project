package com.example.yogastudioproject.controller;

import com.example.yogastudioproject.domain.model.AppUser;
import com.example.yogastudioproject.dto.AppUserDto;
import com.example.yogastudioproject.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@RolesAllowed({"ROLE_ADMIN"})
public class EmployeeController {
    private final AppUserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/create")
    public ResponseEntity<AppUser> registrationEmployee(@Valid @RequestBody AppUserDto appUserDto, Principal principal) {
        AppUser appUser = userService.createEmployee(appUserDto, principal);
        return ResponseEntity.ok().body(appUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<AppUserDto> detailsEmployee(@PathVariable("userId") Long userId) {
        AppUser appUser = userService.getAppUserById(userId);
        return ResponseEntity.ok().body(modelMapper.map(appUser, AppUserDto.class));
    }

    @DeleteMapping("/{userId}/delete")
    public void deleteEmployee(@PathVariable("userId") Long userId) {
        userService.deleteUserById(userId);
    }



    @GetMapping("/all")
    public ResponseEntity<List<AppUser>> getAllUsers() {

        return ResponseEntity.ok().body(userService.getAllUsers());
    }

//    @PatchMapping("/update")
//    public ResponseEntity<AppUser> updateUser(@Valid @RequestBody AppUserDto appUserDto) {
//        return ResponseEntity.ok().body();
//    }

}
