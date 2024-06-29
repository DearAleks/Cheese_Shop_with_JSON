package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    private static Scanner scanner = new Scanner(System.in);
    private static CheeseShop cheeseShop = new CheeseShop();

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
            cheeseShop.addCheeseToShop(cheese);
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
            cheeseShop.removeCheeseFromShop(id);
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

            cheeseShop.updateCheese(id, name, price, quantity);
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
        } catch (InputMismatchException e) {
            System.out.println("\nInvalid input. Please enter correct ID.");
            scanner.nextLine();
        }
    }

    public static void checkout() {
        cheeseShop.printCart();
        System.out.println("Total to pay: " + cheeseShop.checkout());
        cheeseShop.clearCart();
        System.out.println("Thank you for your purchase!");
    }

}

