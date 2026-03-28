package com.sdwema.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of(
                "message", "Smart Digital Wallet Backend is running",
                "walletsApi", "/api/wallets",
                "expensesApi", "/api/expenses",
                "h2Console", "/h2-console"
        );
    }
}
