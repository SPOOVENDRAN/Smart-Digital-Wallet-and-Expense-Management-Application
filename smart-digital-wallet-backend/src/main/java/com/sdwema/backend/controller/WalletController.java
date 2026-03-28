package com.sdwema.backend.controller;

import com.sdwema.backend.dto.AmountRequest;
import com.sdwema.backend.dto.CreateWalletRequest;
import com.sdwema.backend.model.Wallet;
import com.sdwema.backend.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public Wallet createWallet(@Valid @RequestBody CreateWalletRequest request) {
        return walletService.createWallet(request);
    }

    @GetMapping
    public List<Wallet> getAllWallets() {
        return walletService.getAllWallets();
    }

    @GetMapping("/{id}")
    public Wallet getWalletById(@PathVariable Long id) {
        return walletService.getWalletById(id);
    }

    @PostMapping("/{id}/deposit")
    public Wallet deposit(@PathVariable Long id, @Valid @RequestBody AmountRequest request) {
        return walletService.deposit(id, request.getAmount());
    }

    @PostMapping("/{id}/withdraw")
    public Wallet withdraw(@PathVariable Long id, @Valid @RequestBody AmountRequest request) {
        return walletService.withdraw(id, request.getAmount());
    }

    @GetMapping("/{id}/balance")
    public Map<String, BigDecimal> getBalance(@PathVariable Long id) {
        Wallet wallet = walletService.getWalletById(id);
        return Map.of("balance", wallet.getBalance());
    }
}
