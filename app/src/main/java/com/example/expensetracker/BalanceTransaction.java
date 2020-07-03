package com.example.expensetracker;

public class BalanceTransaction {

    private String email;
    private String date;
    private String balance;

    public BalanceTransaction(){

    }

    public BalanceTransaction(String email, String date, String balance) {
        this.email = email;
        this.date = date;
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public String getBalance() {
        return balance;
    }

    public String getDate() {
        return date;
    }
}
