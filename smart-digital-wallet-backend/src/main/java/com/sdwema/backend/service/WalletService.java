package com.sdwema.backend.service;

import com.sdwema.backend.dto.CreateWalletRequest;
import com.sdwema.backend.exception.BusinessException;
import com.sdwema.backend.exception.ResourceNotFoundException;
import com.sdwema.backend.model.Wallet;
import com.sdwema.backend.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(CreateWalletRequest request) {
        BigDecimal initialBalance = request.getInitialBalance() == null ? BigDecimal.ZERO : request.getInitialBalance();
        Wallet wallet = new Wallet(request.getOwnerName(), initialBalance);
        return walletRepository.save(wallet);
    }

    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

    public Wallet getWalletById(Long id) {
        return walletRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found with id: " + id));
    }

    @Transactional
    public Wallet deposit(Long walletId, BigDecimal amount) {
        Wallet wallet = getWalletById(walletId);
        wallet.setBalance(wallet.getBalance().add(amount));
        return wallet;
    }

    @Transactional
    public Wallet withdraw(Long walletId, BigDecimal amount) {
        Wallet wallet = getWalletById(walletId);
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new BusinessException("Insufficient wallet balance");
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));
        return wallet;
    }
}
