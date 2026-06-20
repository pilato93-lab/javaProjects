package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AccountController {

    @GetMapping("/status")
    public String checkStatus() {
        return "Pilato Bank API is live and securely running on the web!";
    }
}