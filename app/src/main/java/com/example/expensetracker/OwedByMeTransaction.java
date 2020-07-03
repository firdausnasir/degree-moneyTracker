package com.example.expensetracker;

public class OwedByMeTransaction {

    String email;
    String name;
    String amount;

    public OwedByMeTransaction(){

    }

    public OwedByMeTransaction(String email, String name, String amount) {
        this.email = email;
        this.name = name;
        this.amount = amount;
    }

    public String getEmail() { return email; }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }
}
