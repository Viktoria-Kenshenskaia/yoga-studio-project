package com.example.yogastudioproject;

import com.example.yogastudioproject.domain.model.AppUser;
import com.example.yogastudioproject.domain.model.Role;
import com.example.yogastudioproject.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class YogaStudioProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(YogaStudioProjectApplication.class, args);
    }

    @Bean
    CommandLineRunner run(AppUserService userService) {
        return args -> {
          userService.createUser(new AppUser(null, "mary", "mary@mail.com", "1234", new ArrayList<>()));
          userService.createUser(new AppUser(null, "anna", "anna@mail.com", "1234", new ArrayList<>()));
          userService.createUser(new AppUser(null, "johan", "johan@mail.com", "1234", new ArrayList<>()));
          userService.createUser(new AppUser(null, "albert", "albert@mail.com", "1234", new ArrayList<>()));

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
