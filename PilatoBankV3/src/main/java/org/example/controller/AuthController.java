package org.example.controller;

import org.example.Client;
import org.example.ClientDAO;
import org.example.SecurityUtils; // Ensure this import is present
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ApiResponse<Client> loginClient(@RequestBody LoginRequest incomingData) {

        ClientDAO dao = new ClientDAO();
        Client foundClient = dao.getClientByAccountNumber(incomingData.getAccountNumber());


        if (foundClient != null && SecurityUtils.verifyPin(incomingData.getPin(), foundClient.getPin())) {
            return new ApiResponse<>("SUCCESS", "Welcome to Pilato Bank!", foundClient);
        } else {
            return new ApiResponse<>("FAILED", "Invalid Account Number or PIN.", null);
        }
    }
}