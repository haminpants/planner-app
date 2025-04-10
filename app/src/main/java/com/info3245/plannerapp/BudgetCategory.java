package com.info3245.plannerapp;

public class BudgetCategory {
    private String name;
    private double budget;
    private double spent;

    public BudgetCategory (String name, double budget, double spent) {
        this.name = name;
        this.budget = budget;
        this.spent = spent;
    }

    public String getName() {
        return name;
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
