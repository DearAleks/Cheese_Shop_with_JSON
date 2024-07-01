package org.example;
/* 1. contains money the customer has
2. contains the items the customer owns.
3. Whenever the customer buys something, money is reduced.
4. If customer doesn't have any money left, then notify the user about it.*/

import java.math.BigDecimal;

public class Customer {
    private int customerId;
    private BigDecimal balance;

    public Customer(int customerId, BigDecimal balance) {
        this.customerId = customerId;
        this.balance = balance;
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

}
