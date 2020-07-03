package com.example.expensetracker;

public class OwedToMeTransaction {

    String email;
    String name;
    String amount;

    public OwedToMeTransaction(){

    }

    public OwedToMeTransaction(String email, String name, String amount) {
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
