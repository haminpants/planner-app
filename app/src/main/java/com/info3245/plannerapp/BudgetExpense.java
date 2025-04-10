package com.info3245.plannerapp;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BudgetExpense {
    private String name;
    private BigDecimal budgetLimit;
    private BigDecimal amountSpent;
    private LocalDate date;

    // Constructor to initialize expense
    public BudgetExpense(String name, BigDecimal budgetLimit, BigDecimal amountSpent, LocalDate date) {
        this.name = name;
        this.budgetLimit = budgetLimit;
        this.amountSpent = amountSpent;
        this.date = date;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for budget limit
    public BigDecimal getBudgetLimit() {
        return budgetLimit;
    }

    // Getter for amount spent
    public BigDecimal getAmountSpent() {
        return amountSpent;
    }

    // Getter for date
    public LocalDate getDate() {
        return date;
    }

    // Method to update amount spent
    public void addExpense(BigDecimal amount) {
        this.amountSpent = this.amountSpent.add(amount);
    }

    // Method to calculate progress as a percentage of budget spent
    public int getProgress() {
        if (budgetLimit.compareTo(BigDecimal.ZERO) > 0) {
            return (int) (amountSpent.doubleValue() / budgetLimit.doubleValue() * 100);
        } else {
            return 0;
        }
    }
}