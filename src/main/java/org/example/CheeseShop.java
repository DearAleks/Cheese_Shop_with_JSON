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

    public ArrayList<Cheese> getCheeseList(String json) {
        Gson gson = new Gson();
        cheeseList = gson.fromJson(json, new TypeToken<ArrayList<Cheese>>() {}.getType());
        return cheeseList;
    }

    public void addCheeseToShop(Cheese cheese) {
        for (Cheese cheeseInInventory : cheeseList) {
            if (cheeseInInventory.getId() == cheese.getId()) {
                System.out.println("org.example.Cheese with ID " + cheese.getId() + " already exists in the inventory.");
                return;
            }
        }
        cheeseList.add(cheese);
        System.out.println("org.example.Cheese with the ID " + cheese.getId() + " added to the inventory.");
    }

    public void removeCheeseFromShop(int id) {
        ArrayList<Cheese> found = new ArrayList<>();
        for (Cheese cheese : cheeseList) {
            if (cheese.getId() == id) {
                found.add(cheese);
            }
        }
        if (!found.isEmpty()) {
            cheeseList.removeAll(found);
            System.out.println("Removed cheese with ID : " + id);
        } else {
            System.out.println("org.example.Cheese with ID " + id + " not found");
        }
    }

    public void updateCheese(int id, String name, double price, double quantity) {
        for (Cheese cheese : cheeseList) {
            if (cheese.getId() == id) {
                cheese.setName(name);
                cheese.setPrice(price);
                cheese.setQuantity(quantity);
                return;
            }
        }
    }
    public boolean addCheeseToCart(int id, double boughtQuantity) {
        for (Cheese cheese : cheeseList) {
            if (cheese.getId() == id && cheese.getQuantity() >= boughtQuantity) {
                Cheese cheeseToCart = new Cheese(cheese.getId(), cheese.getName(), cheese.getPrice(), boughtQuantity);
                cheeseToCart.setQuantity(boughtQuantity);
                cheese.setQuantity(cheese.getQuantity() - boughtQuantity);
                cart.add(cheeseToCart);
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

    public BigDecimal checkout() {
        double totalCost = 0.0d;
        BigDecimal roundedTotalCost = null;
        for (Cheese cheese : cart) {
            totalCost += cheese.getQuantity() * cheese.getPrice();
            roundedTotalCost = new BigDecimal(totalCost).setScale(2, RoundingMode.HALF_UP);
        }
        return roundedTotalCost;
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

    public void cartToJson() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("Cart.json")) {
            gson.toJson(cheeseList, writer);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
