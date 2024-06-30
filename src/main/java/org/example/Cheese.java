package org.example;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class Cheese implements Serializable {

    transient private static AtomicInteger idCounter = new AtomicInteger(0);
    private int id;
    private String name;
    private double price;
    private double quantity;


    public Cheese(String name, double price, double quantity){
        this.id = idCounter.incrementAndGet();
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    public int getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getPrice() {
        return price;
    }
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    public double getQuantity() {
        return Double.parseDouble(String.format("%.2f", quantity));
    }

    @Override
    public String toString() {
        return "ID: " + id + " / Name: " + name + " / Price per kg: " + price + " / Quantity: " + getQuantity() + " kg";
    }
}


