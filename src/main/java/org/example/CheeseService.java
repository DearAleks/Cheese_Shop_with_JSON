package org.example;

import java.util.ArrayList;

public class CheeseService {
    private CheeseShop cheeseShop;

    public CheeseService(CheeseShop cheeseShop) {
        this.cheeseShop = cheeseShop;
    }

    public void addCheeseToShop(Cheese cheese) {
        for (Cheese cheeseInInventory :cheeseShop.getCheeseList()) {
            if (cheeseInInventory.getId() == cheese.getId()) {
                System.out.println("org.example.Cheese with ID " + cheese.getId() + " already exists in the inventory.");
                return;
            }
        }
        cheeseShop.getCheeseList().add(cheese);
        System.out.println("org.example.Cheese with the ID " + cheese.getId() + " added to the inventory.");
    }

    public void removeCheeseFromShop(int id) {
        ArrayList<Cheese> found = new ArrayList<>();
        for (Cheese cheese : cheeseShop.getCheeseList()) {
            if (cheese.getId() == id) {
                found.add(cheese);
            }
        }
        if (!found.isEmpty()) {
            cheeseShop.getCheeseList().removeAll(found);
            System.out.println("Removed cheese with ID : " + id);
        } else {
            System.out.println("org.example.Cheese with ID " + id + " not found");
        }
    }

    public void updateCheese(int id, String name, double price, double quantity) {
        for (Cheese cheese : cheeseShop.getCheeseList()) {
            if (cheese.getId() == id) {
                cheese.setName(name);
                cheese.setPrice(price);
                cheese.setQuantity(quantity);
                return;
            }
        }
    }
}
