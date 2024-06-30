package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.math.BigDecimal;

public class CheeseShop {
    private ArrayList<Cheese> cheeseList = new ArrayList<>();
    private ArrayList<Cheese> cart = new ArrayList<>();

    public ArrayList<Cheese> getCheeseList() {
        return cheeseList;
    }

    public ArrayList<Cheese> getCart() {
        return cart;
    }

    public void printCheeseList() {
        System.out.println("Cheeses in the inventory: ");
        for (Cheese cheese : cheeseList){
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
                inventoryToJson();
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
        cart.clear();
    }

    public void inventoryToJson() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("Inventory.json")) {
            gson.toJson(cheeseList, writer);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void inventoryFromJson() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("Inventory.json")) {
            cheeseList = gson.fromJson(reader, new TypeToken<ArrayList<Cheese>>() {}.getType());
            printCheeseList();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
