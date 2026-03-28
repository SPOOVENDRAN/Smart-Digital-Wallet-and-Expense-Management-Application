package com.sdwema.backend.service;

import com.sdwema.backend.dto.CreateExpenseRequest;
import com.sdwema.backend.exception.ResourceNotFoundException;
import com.sdwema.backend.model.Expense;
import com.sdwema.backend.model.ExpenseCategory;
import com.sdwema.backend.model.Wallet;
import com.sdwema.backend.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final WalletService walletService;

    public ExpenseService(ExpenseRepository expenseRepository, WalletService walletService) {
        this.expenseRepository = expenseRepository;
        this.walletService = walletService;
    }

    @Transactional
    public Expense createExpense(CreateExpenseRequest request) {
        Wallet wallet = walletService.withdraw(request.getWalletId(), request.getAmount());

        Expense expense = new Expense();
        expense.setWallet(wallet);
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setDescription(request.getDescription());

        return expenseRepository.save(expense);
    }

    public List<Expense> getExpenses(Long walletId, LocalDate fromDate, LocalDate toDate, ExpenseCategory category) {
        if (walletId == null) {
            return expenseRepository.findAll();
        }
        if (fromDate != null && toDate != null) {
            return expenseRepository.findByWallet_IdAndExpenseDateBetween(walletId, fromDate, toDate);
        }
        if (category != null) {
            return expenseRepository.findByWallet_IdAndCategory(walletId, category);
        }
        return expenseRepository.findByWallet_Id(walletId);
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
    }

    @Transactional
    public void deleteExpense(Long id) {
        Expense expense = getExpenseById(id);
        walletService.deposit(expense.getWallet().getId(), expense.getAmount());
        expenseRepository.delete(expense);
    }
}
