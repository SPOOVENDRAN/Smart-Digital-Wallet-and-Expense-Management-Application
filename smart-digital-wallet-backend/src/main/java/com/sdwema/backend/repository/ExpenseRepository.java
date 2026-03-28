package com.sdwema.backend.repository;

import com.sdwema.backend.model.Expense;
import com.sdwema.backend.model.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long>, JpaSpecificationExecutor<Expense> {
    List<Expense> findByWallet_Id(Long walletId);

    List<Expense> findByWallet_IdAndExpenseDateBetween(Long walletId, LocalDate fromDate, LocalDate toDate);

    List<Expense> findByWallet_IdAndCategory(Long walletId, ExpenseCategory category);
}
