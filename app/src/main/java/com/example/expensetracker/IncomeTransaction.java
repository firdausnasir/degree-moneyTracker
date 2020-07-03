package com.example.expensetracker;

import android.content.Context;

public class IncomeTransaction {

    String email;
    String category;
    String currDate;
    String note;
    String amount;

    public IncomeTransaction(){

    }

    public IncomeTransaction(String email, String category, String note, String currdate, String amount) {
        this.email = email;
        this.category = category;
        this.note = note;
        this.currDate = currdate;
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
