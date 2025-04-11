package com.info3245.plannerapp;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BudgetExpense {
    private String name;
    private double budgetLimit;
    private double amountSpent;
    private LocalDate date;

    // Constructor to initialize expense
    public BudgetExpense(String name, double budgetLimit, double amountSpent, LocalDate date) {
        this.name = name;
        this.budgetLimit = budgetLimit;
        this.amountSpent = amountSpent;
        this.date = date;
    }


    public String getName() {
        return name;
    }

    public double getBudgetLimit() {
        return budgetLimit;
    }

    public double getAmountSpent() {
        return amountSpent;
    }

    public LocalDate getDate() {
        return date;
    }

    // Method to update amount spent
    public void addExpense(double amount) {
        this.amountSpent += amount;
    }

    // Method to calculate progress as a percentage of budget spent
    public int getProgress() {
        if (budgetLimit > 0) {
            return (int) ((amountSpent / budgetLimit) * 100);
        } else {
            return 0;
        }
    }
}