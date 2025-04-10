package com.info3245.plannerapp;

public class BudgetExpense {
    private String description;
    private double budget;
    private double spent;

    public BudgetExpense (String description                                                                                                                                                                                                                                                     , double budget, double spent) {
        this.description = description;
        this.budget = budget;
        this.spent = spent;
    }

    public String getDescription() {
        return description;
    }

    public double getBudget() {
        return budget;
    }
    public double getSpent(){
        return spent;
    }

    public void addExpense(double amount) {
        this.spent += amount;
    }

}

