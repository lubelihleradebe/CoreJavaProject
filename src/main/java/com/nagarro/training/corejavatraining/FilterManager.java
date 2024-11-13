package com.nagarro.training.corejavatraining;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentMap;

import com.nagarro.training.corejavatraining.Interfaces.FilterStrategy;
import com.nagarro.training.corejavatraining.models.CompositeKey;
import com.nagarro.training.corejavatraining.models.Product;

public class FilterManager {

    private final FilterStrategy filterStrategy;

    public FilterManager(FilterStrategy filterStrategy
    ) {
        this.filterStrategy = filterStrategy;
    }

    public List<Product> filterProducts(int choice, Scanner scanner, ConcurrentMap<CompositeKey, Product> productMap) {
        Map<String, String> criteria;
        criteria = new HashMap<>();

        switch (choice) {
            case 1:
                System.out.print("Prompt: Enter COLOR: ");
                criteria.put("color", scanner.nextLine().trim().toLowerCase());
                break;
            case 2:
                System.out.print("Prompt: Enter SIZE: ");
                criteria.put("size", scanner.nextLine().trim().toLowerCase());
                break;
            case 3:
                System.out.print("Prompt: Enter COLOR: ");
                criteria.put("color", scanner.nextLine().trim().toLowerCase());
                System.out.print("Enter SIZE: ");
                criteria.put("size", scanner.nextLine().trim().toLowerCase());
                break;
            case 4:
                System.out.print("Prompt: Enter BRAND: ");
                criteria.put("brand", scanner.nextLine().trim().toLowerCase());
                System.out.print("Enter COLOR: ");
                criteria.put("color", scanner.nextLine().trim().toLowerCase());
                break;
            case 5:
                System.out.print("Prompt: Enter BRAND: ");
                criteria.put("brand", scanner.nextLine().trim().toLowerCase());
                System.out.print("Enter SIZE: ");
                criteria.put("size", scanner.nextLine().trim().toLowerCase());
                break;
            case 6:
                System.out.print("Prompt: Enter TYPE: ");
                criteria.put("type", scanner.nextLine().trim().toLowerCase());
                break;
            default:
                System.out.println("Invalid option. Try again.");
                return null;
        }

        try {
            return filterStrategy.filter(criteria, productMap);
        } catch (Exception e) {
            System.out.println("An error occurred while filtering products: " + e.getMessage());
            return null;
        }
    }
}
