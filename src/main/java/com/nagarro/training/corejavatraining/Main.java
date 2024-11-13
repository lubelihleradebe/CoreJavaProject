package com.nagarro.training.corejavatraining;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.nagarro.training.corejavatraining.ConcreteStrategies.ConcreteFilterStrategy;
import com.nagarro.training.corejavatraining.Interfaces.FilterStrategy;
import com.nagarro.training.corejavatraining.models.CompositeKey;
import com.nagarro.training.corejavatraining.models.Product;
import com.nagarro.training.corejavatraining.watcher.FileWatcher;



public class Main {

    public static void main(String[] args) {

        
         // Path to the directory to watch
        Path directoryPath = Paths.get("./src/main/resources/data/");

        // Create productMap to hold products
        ConcurrentMap<CompositeKey, Product> productMap = new ConcurrentHashMap<>();

        // Create and start the FileWatcher in a new thread
        FileWatcher nikeWatcher = new FileWatcher(directoryPath, "^nike-\\d+\\.csv$", productMap);
        FileWatcher pumaWatcher = new FileWatcher(directoryPath, "^puma-\\d+\\.csv$", productMap);

        nikeWatcher.processInitialFiles();
        pumaWatcher.processInitialFiles();

        Thread nikeWatcherThread = new Thread(nikeWatcher); 
        Thread pumaWatcherThread = new Thread(pumaWatcher);
        nikeWatcherThread.start(); // Start watching the directory for new files
        pumaWatcherThread.start(); // Start watching the directory for new files
        // Sample productMap (this should ideally be populated by the FileWatcher)
        // ConcurrentMap<String, Product> productMap = new ConcurrentHashMap<>();
        try (Scanner scanner = new Scanner(System.in)) {
            
            
            while (true) {
                System.out.println("Select filter option:");
                System.out.println("1. COLOR");
                System.out.println("2. SIZE");
                System.out.println("3. COLOR AND SIZE");
                System.out.println("4. BRAND AND COLOR");
                System.out.println("5. BRAND AND SIZE");
                System.out.println("6. EXIT");
                
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                
                if (choice == 6) {
                    System.out.println("Exiting program.");
                    break;
                }
                
                Map<String, String> criteria = new HashMap<>();
                FilterStrategy filterStrategy = new ConcreteFilterStrategy(); // Use the dynamic strategy for any combination
                
                switch (choice) {
                    case 1:
                        System.out.print("Enter COLOR: ");
                        criteria.put("color", scanner.nextLine().trim());
                        break;
                    case 2:
                        System.out.print("Enter SIZE: ");
                        criteria.put("size", scanner.nextLine().trim());
                        break;
                    case 3:
                        System.out.print("Enter COLOR: ");
                        criteria.put("color", scanner.nextLine().trim());
                        System.out.print("Enter SIZE: ");
                        criteria.put("size", scanner.nextLine().trim());
                        break;
                    case 4:
                        System.out.print("Enter BRAND: ");
                        criteria.put("brand", scanner.nextLine().trim());
                        System.out.print("Enter COLOR: ");
                        criteria.put("color", scanner.nextLine().trim());
                        break;
                    case 5:
                        System.out.print("Enter BRAND: ");
                        criteria.put("brand", scanner.nextLine().trim());
                        System.out.print("Enter SIZE: ");
                        criteria.put("size", scanner.nextLine().trim());
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                        continue;
                }
                
                // add something here
                // System.out.println("creteria is : "+criteria);
                // System.out.println(criteria.values());
                // System.out.println(criteria.keySet());
                
                List<Product> filteredProducts = filterStrategy.filter(criteria, productMap);
                System.out.println("Filtered Products: ");
                System.out.println(filteredProducts.size());
                filteredProducts.forEach(product -> System.out.println(product));
            }
        }
    }
}

