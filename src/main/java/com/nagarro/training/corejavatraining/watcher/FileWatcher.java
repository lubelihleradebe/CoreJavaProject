package com.nagarro.training.corejavatraining.watcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.ConcurrentMap;

import com.nagarro.training.corejavatraining.Product;


/**
 * Responsible for watching a directory for new files and processing them.
 * 
 * As soon as the program starts two thread, one for puma and the other for nike, store the data 
 * in an in-memory data structure so that the write/read operations are optimized with better READ performance
 */
public class FileWatcher implements Runnable {

    private final Path directoryPath; // Directory to watch
    private final String filePattern; // Regex pattern for file name
    private final ConcurrentMap<String, Product> productMap; // In-memory data structure to store products
    
    public FileWatcher(Path directoryPath, String filePattern, ConcurrentMap<String, Product> productMap) {
        this.directoryPath = directoryPath;
        this.filePattern = filePattern; // Get file pattern from strategy
        this.productMap = productMap;
    }
    

    @Override
    public void run() {
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            directoryPath.register(watchService, 
            StandardWatchEventKinds.ENTRY_CREATE, 
            StandardWatchEventKinds.ENTRY_MODIFY); // Register directory with watch service    
             
            System.out.println("Watching directory: " + directoryPath);

            while(true){
                System.out.println("hello");
                WatchKey key = watchService.take(); // Wait for a watch key to be signaled

                for (WatchEvent<?> event : key.pollEvents()) {
                    Path filePath = directoryPath.resolve((Path) event.context()); // Get the file path
                    String fileName = filePath.getFileName().toString(); // Get the file name

                    System.out.println("Event kind: " + event.kind() + ". File affected: " + fileName);

                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE || event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                        // Handle files
                        if (filePattern.matches(fileName)) {
                            System.out.println(fileName);
                            processFile(filePath);
                        }
                        
                    }
                }
                if (!key.reset()) break; // Reset the key
            }
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
   
    

   /**
     * Processes a specific file by reading its content and storing it in the products map.
     * @param filePath the path of the file to process
     * @param brand    the brand associated with the file
     */
    private void processFile(Path filePath) {
        System.out.println(" file: " + filePath);

        // Open the file for reading
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            
            // Read each line in the file
            while ((line = reader.readLine()) != null) {
                // Split the line by commas to extract each product attribute
                String[] columns = line.split(",");
                if (columns.length < 4) {
                    System.err.println("Invalid line format in file: " + filePath + " - " + line);
                    continue; // Skip lines that do not have enough data
                }
                
                // Parse the attributes from columns
                String id = columns.length > 0 ? columns[0] : "";
                String brand = columns.length > 1 ? columns[1] : "";
                String color = columns.length > 2 ? columns[2] : "";
                String size = columns.length > 3 ? columns[3] : "";
                String type = columns.length > 4 ? columns[4] : "";
                double price = columns.length > 5 ? Double.parseDouble(columns[5]) : 0.0;

                // Create a new Product instance
                Product product = price > 0 
                    ? new Product(brand, color, size, price, type) 
                    : new Product(brand, color, size, type);

                // Store the product in the productMap using the ID as the key
                // productMap.putIfAbsent(id, product);
                productMap.put(id, product);
                System.out.println("Product Map size: " + productMap.size());
                System.out.println("Added product: " + product);  // Debug statement
            }
            
        } catch (IOException ex) {
            System.err.println("Error reading file: " + filePath + " - " + ex.getMessage());
        }
    }

    public ConcurrentMap<String, Product> getProductMap() {
        System.out.println("here: "+productMap.size());
        return productMap;
    }
    
}

    


