package org.example;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    private static Scanner scanner = new Scanner(System.in);
    private static CheeseShop cheeseShop = new CheeseShop();
    private static CheeseService cheeseService = new CheeseService(cheeseShop);
    private static Customer customer = new Customer(12345678, new BigDecimal("5.00"));

    public static void main( String[] args ) {

        while (true) {
            printMenu();
            try {
                int action = scanner.nextInt();
                scanner.nextLine();
                if (action == 1) {
                    addCheese();
                } else if (action == 2) {
                    deleteCheese();
                } else if (action == 3) {
                    updateInventory();
                } else if (action == 4) {
                    cheeseShop.inventoryFromJson();
                } else if (action == 5) {
                    addToCart();
                } else if (action == 6) {
                    removeFromCart();
                } else if (action == 7) {
                    cheeseShop.printCart();
                } else if (action == 8) {
                    checkout();
                } else if (action == 9) {
                    System.out.println("Exiting the shop. Goodbye!");
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }
    public static void printMenu() {
        System.out.println("\nTo add an item to the shop - 1");
        System.out.println("To delete an item from the shop - 2");
        System.out.println("To update an item - 3");
        System.out.println("To check the stock - 4");
        System.out.println("To add an item to the cart - 5");
        System.out.println("To delete an item from the cart - 6");
        System.out.println("To review the items in the cart - 7");
        System.out.println("To checkout - 8");
        System.out.println("To exit the shop - 9");
    }
    public static void addCheese() {
        try {
            System.out.println("Provide cheese id");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Provide cheese name");
            String name = scanner.nextLine();

            System.out.println("Provide cheese price per kg");
            double price = scanner.nextDouble();

            System.out.println("Provide cheese quantity in kg");
            double quantity = scanner.nextDouble();

            Cheese cheese = new Cheese(id, name, price, quantity);
            cheeseService.addCheeseToShop(cheese);
            cheeseShop.inventoryToJson();

        } catch (InputMismatchException e) {
            System.out.println("\nInvalid input. Please enter correct values.");
            scanner.nextLine();
        }
    }
    public static void deleteCheese(){
        try {
            System.out.println("Provide cheese id to delete: ");
            int id = scanner.nextInt();
            cheeseService.removeCheeseFromShop(id);
            cheeseShop.inventoryToJson();
        } catch (InputMismatchException e) {
            System.out.println("\nInvalid input. Please enter correct ID.");
            scanner.nextLine();
        }
    }

    public static void updateInventory(){
        try {
            System.out.println("Provide cheese ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Provide cheese name to update: ");
            String name = scanner.nextLine();

            System.out.println("Provide cheese price to update: ");
            double price = scanner.nextDouble();

            System.out.println("Provide cheese quantity to update: ");
            double quantity = scanner.nextDouble();

            cheeseService.updateCheese(id, name, price, quantity);
            System.out.println("org.example.Cheese with ID " + id + " updated successfully.");
            cheeseShop.inventoryToJson();
        } catch (InputMismatchException e) {
            System.out.println("\nInvalid input. Please enter correct value.");
            scanner.nextLine();
        }
    }
    public static void addToCart(){
        try {
            System.out.println("Provide ID of cheese that you want to buy: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Provide the quantity of cheese that you want to buy in kg: ");
            double boughtQuantity = scanner.nextDouble();

            if (cheeseShop.addCheeseToCart(id, boughtQuantity)) {
                System.out.println("org.example.Cheese with ID " + id + " added to cart.");
            } else {
                System.out.println("org.example.Cheese with ID " + id + " not found.");
            }
        } catch (InputMismatchException e) {
            System.out.println("\nInvalid input. Please enter correct ID.");
            scanner.nextLine();
        }
    }

    public static void removeFromCart() {
        try {
            System.out.println("Provide ID of cheese that you want to remove from cart: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            cheeseShop.removeCheeseFromCart(id);
            cheeseShop.inventoryToJson();
        } catch (InputMismatchException e) {
            System.out.println("\nInvalid input. Please enter correct ID.");
            scanner.nextLine();
        }
    }

    public static void checkout() {
        cheeseShop.printCart();
        BigDecimal totalCost = cheeseShop.checkout();
        System.out.println("Total to pay: " + totalCost);
        if (customer.withdraw(totalCost)) {
            customer.addToPurchases(cheeseShop.getCart());
            customer.purchasesToJson();
            cheeseShop.clearCart();
            System.out.println("Thank you for your purchase!");
        } else {
            System.out.println("You don't have enough money, please review your cart.");
        }
    }
}

