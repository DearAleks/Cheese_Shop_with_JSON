package org.example;
/* 1. contains money the customer has
2. contains the items the customer owns.
3. Whenever the customer buys something, money is reduced.
4. If customer doesn't have any money left, then notify the user about it.*/

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Customer {
    private int customerId;
    private BigDecimal balance;
    private ArrayList<Cheese> purchases;

    public Customer(int customerId, BigDecimal balance) {
        this.customerId = customerId;
        this.balance = balance;
        this.purchases = new ArrayList<>();
    }

    public BigDecimal getBalance() {
        return this.balance;
    }
    public boolean withdraw(BigDecimal howMuch) {
        if (balance.compareTo(howMuch) >= 0) {
            balance = balance.subtract(howMuch);
            return true;
        } else {
            return false;
        }
    }

    public void addToPurchases(ArrayList<Cheese> cart) {
        purchases.addAll(cart);
    }

    public void purchasesToJson() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("PurchasesHistory.json")) {
            gson.toJson(purchases, writer);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
