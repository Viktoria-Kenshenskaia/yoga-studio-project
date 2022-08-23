package com.example.yogastudioproject;

import com.example.yogastudioproject.domain.model.AppUser;
import com.example.yogastudioproject.domain.model.Role;
import com.example.yogastudioproject.dto.AddressDto;
import com.example.yogastudioproject.dto.CompanyDto;
import com.example.yogastudioproject.service.AppUserService;
import com.example.yogastudioproject.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

@SpringBootApplication
public class YogaStudioProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(YogaStudioProjectApplication.class, args);
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    CommandLineRunner run(AppUserService userService, CompanyService companyService) {
        return args -> {
          userService.createUser(new AppUser(null, "mary", "johns", LocalDate.parse("1995-03-12"), "mary@mail.com", "123456", null, null, new HashSet<>()));
          userService.createUser(new AppUser(null, "anna", "smith", LocalDate.parse("1990-08-23"),  "anna@mail.com", "123456", null, null,  new HashSet<>()));
          userService.createUser(new AppUser(null, "johan", "swartz", LocalDate.parse("1987-01-06"), "johan@mail.com", "123456", null, null,  new HashSet<>()));
          userService.createUser(new AppUser(null, "albert", "seuberg", LocalDate.parse("1990-08-30"), "albert@mail.com", "123456",null, null, new HashSet<>()));

          userService.saveRole(new Role(null, "ROLE_ADMIN"));
          userService.saveRole(new Role(null, "ROLE_MANAGER"));
          userService.saveRole(new Role(null, "ROLE_TEACHER"));



          userService.addRoleToUser("mary@mail.com", "ROLE_ADMIN");
          userService.addRoleToUser("mary@mail.com", "ROLE_MANAGER");
          userService.addRoleToUser("anna@mail.com", "ROLE_MANAGER");
          userService.addRoleToUser("johan@mail.com", "ROLE_TEACHER");
          userService.addRoleToUser("albert@mail.com", "ROLE_TEACHER");



        };
    }

}
