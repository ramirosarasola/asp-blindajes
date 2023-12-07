package com.example.aspblindajes.auth;

import com.example.aspblindajes.model.User;
import com.example.aspblindajes.service.UserServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.aspblindajes.model.Role.ADMIN;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserServiceImpl userService;
    @Override
    public void run(String... args) throws Exception {
        // Tu lógica para buscar o crear el usuario admin aquí
        Optional<User> adminUserOptional = userService.findUserByUsername("admin");
        if (adminUserOptional.isEmpty()){
            RegisterRequest registerAdmin = new RegisterRequest("ASP","Blindajes", "admin", "ccasp2023",ADMIN);
            authenticationService.register(registerAdmin);
        }
    }
}

