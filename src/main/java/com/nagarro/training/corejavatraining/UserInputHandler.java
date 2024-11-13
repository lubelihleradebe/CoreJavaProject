package com.nagarro.training.corejavatraining;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class UserInputHandler {

    private final Scanner scanner;

    public UserInputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public int getUserChoice(){
        System.out.println("Select filter option:");
        System.out.println("0. EXIT");
        System.out.println("1. COLOR");
        System.out.println("2. SIZE");
        System.out.println("3. COLOR AND SIZE");
        System.out.println("4. BRAND AND COLOR");
        System.out.println("5. BRAND AND SIZE");
        System.out.println("6. TYPE"); // The type of product

        return validateChoice();
    }

        public int validateChoice() {
            AtomicInteger choice = new AtomicInteger(-1);
            boolean validChoice = false;

            // Handling invalid input for choice selection
            while (!validChoice) {
                try {
                    System.out.print("User: ");
                    String input = scanner.nextLine().trim(); // Read input as string
                    if (input.isEmpty()) {
                        throw new IllegalArgumentException("Choice cannot be empty. Please enter a valid number.");
                    }
                    choice.set(Integer.parseInt(input)); // Try to parse input as an integer
                    if (choice.get() < 0 || choice.get() > 6) {
                        throw new IllegalArgumentException("Please enter a number between 0 and 6.");
                    }
                    validChoice = true; // Valid choice, exit loop
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number between 0 and 6.");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            return choice.get();
        }
}



    
