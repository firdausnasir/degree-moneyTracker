package com.example.expensetracker;

public class ExpenseTransaction {

    private String email;
    private String category;
    private String currDate;
    private String note;
    private String amount;

    public ExpenseTransaction(){

    }

    public ExpenseTransaction(String email, String category, String currDate, String note, String amount) {
        this.email = email;
        this.category = category;
        this.currDate = currDate;
        this.note = note;
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public String getCategory() {
        return category;
    }

    public String getCurrDate() {
        return currDate;
    }

    public String getNote() {
        return note;
    }

    public String getAmount() {
        return amount;
    }
}
