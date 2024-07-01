package org.example;

import com.google.gson.Gson;

import java.io.*;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.math.BigDecimal;

public class CheeseShop {
    private ArrayList<Cheese> cheeseList;
    private ArrayList<Cheese> cart;
    private ArrayList<Cheese> purchases;
    private static final String DATA_FILE = "JSON.txt";

    public CheeseShop() {
        this.cheeseList = new ArrayList<>();
        this.cart = new ArrayList<>();
        this.purchases = new ArrayList<>();
    }
    public ArrayList<Cheese> getCheeseList() {
        return cheeseList;
    }

    public ArrayList<Cheese> getPurchases() {
        return purchases;
    }

    public void printCheeseList() {
        System.out.println("Cheeses in the inventory: ");
        for (Cheese cheese : cheeseList) {
            System.out.println(cheese);
        }
    }

    public void printCart() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty");
        } else {
            System.out.println("Items in your cart: ");
            for (Cheese cheese : cart) {
                System.out.println(cheese);
            }
        }
    }

    public boolean addCheeseToCart(int id, double boughtQuantity) {
        for (Cheese cheese : cheeseList) {
            if (cheese.getId() == id && cheese.getQuantity() >= boughtQuantity) {
                Cheese cheeseToCart = new Cheese(cheese.getName(), cheese.getPrice(), boughtQuantity);
                cheeseToCart.setQuantity(boughtQuantity);
                cheese.setQuantity(cheese.getQuantity() - boughtQuantity);
                cart.add(cheeseToCart);
                saveDataToJson();
                return true;
            }
        }
        return false;
    }

    public void removeCheeseFromCart(int id) {
        boolean foundInCart = false;
        Cheese cheeseToRemove = null;

        for (Cheese cheese : cart) {
            if (cheese.getId() == id) {
                cheeseToRemove = cheese;
                foundInCart = true;
                break;
            }
        }
        if (foundInCart) {
            double quantityToAddToInventory = cheeseToRemove.getQuantity();
            cart.remove(cheeseToRemove);
            System.out.println("Removed cheese with ID " + id + " from the cart.");

            for (Cheese cheese : cheeseList) {
                if (cheese.getId() == id) {
                    cheese.setQuantity(cheese.getQuantity() + quantityToAddToInventory);
                    break;
                }
            }
        } else {
            System.out.println("org.example.Cheese with ID " + id + " not found in the cart.");
        }
    }

    public BigDecimal checkout() {
        double totalCost = 0.0;
        for (Cheese cheese : cart) {
            totalCost += cheese.getQuantity() * cheese.getPrice();
        }
        return new BigDecimal(totalCost).setScale(2, RoundingMode.HALF_UP);
    }

    public void clearCart() {
        purchases.addAll(cart);
        cart.clear();
        saveDataToJson();
    }

    public void saveDataToJson() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(DATA_FILE)) {
            DataWrapper dataWrapper = new DataWrapper(cheeseList, purchases);
            gson.toJson(dataWrapper, writer);
        } catch (IOException e) {
            System.out.println("Error writing to JSON file: " + e.getMessage());
        }
    }

    public void loadDataFromJson() {
        Gson gson = new Gson();
        File file = new File(DATA_FILE);

        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                DataWrapper dataWrapper = gson.fromJson(reader, DataWrapper.class);
                this.cheeseList = dataWrapper.getCheeseList();
                this.purchases = dataWrapper.getPurchases();
                if (this.cheeseList == null) {
                    this.cheeseList = new ArrayList<>();
                } if (this.purchases == null) {
                    this.purchases = new ArrayList<>();
                }
                printCheeseList();
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Error reading JSON file: " + e.getMessage());
            }
        } else {
            System.out.println("No data file found, starting with empty inventory and cart.");
        }
    }

    private static class DataWrapper {
        private ArrayList<Cheese> cheeseList;
        private ArrayList<Cheese> purchases;

        public DataWrapper(ArrayList<Cheese> cheeseList, ArrayList<Cheese> purchases) {
            this.cheeseList = cheeseList;
            this.purchases = purchases;
        }
        public ArrayList<Cheese> getCheeseList() {
            return cheeseList;
        }

        public ArrayList<Cheese> getPurchases() {
            return purchases;
        }
    }
}