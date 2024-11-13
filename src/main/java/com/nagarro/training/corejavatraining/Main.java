package com.nagarro.training.corejavatraining;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.nagarro.training.corejavatraining.ConcreteStrategies.BrandFilterCriterion;
import com.nagarro.training.corejavatraining.ConcreteStrategies.ColorFilterCriterion;
import com.nagarro.training.corejavatraining.ConcreteStrategies.ConcreteFilterStrategy;
import com.nagarro.training.corejavatraining.ConcreteStrategies.SizeFilterCriterion;
import com.nagarro.training.corejavatraining.ConcreteStrategies.TypeFilterCriterion;
import com.nagarro.training.corejavatraining.Interfaces.FilterCriterion;
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


        List<FilterCriterion> criteriaList = Arrays.asList(
            new ColorFilterCriterion(),
            new SizeFilterCriterion(),
            new BrandFilterCriterion(),
            new TypeFilterCriterion()
        );
        try (Scanner scanner = new Scanner(System.in)) {
            UserInputHandler userInputHandler = new UserInputHandler(scanner);
            FilterManager filterManager = new FilterManager(new ConcreteFilterStrategy(criteriaList)); // Dynamically use strategy
            
            while (true) {
                int choice = userInputHandler.getUserChoice();

                if (choice == 0) {
                    System.out.println("Exiting program.");
                    // Stop the file watchers
                    nikeWatcher.stopWatching();
                    pumaWatcher.stopWatching();
                    nikeWatcherThread.interrupt();
                    pumaWatcherThread.interrupt();
                    try {
                        nikeWatcherThread.join();
                        pumaWatcherThread.join();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Handle thread interruption
                    }
                    break;
                }

                // Get filtered products based on user choice
                List<Product> filteredProducts = filterManager.filterProducts(choice, scanner, productMap);
                if (filteredProducts != null) {
                    filteredProducts.forEach(product -> System.out.println(product.toString()));
                }
            }
        }
    }

    
}

