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

import com.nagarro.training.corejavatraining.CompositeKey;
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
    private final ConcurrentMap<CompositeKey, Product> productMap; // In-memory data structure to store products
    
    public FileWatcher(Path directoryPath, String filePattern, ConcurrentMap<CompositeKey, Product> productMap) {
        this.directoryPath = directoryPath;
        this.filePattern = filePattern; // Get file pattern from strategy
        this.productMap = productMap;
    }

    public void processInitialFiles(){
        try {
            Files.list(directoryPath)
            .filter(path -> path.getFileName().toString().matches(filePattern))
            .forEach(this::processFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    @Override
    public void run() {
        try {

            // Check if the directory exists and exit if it doesn't return
            if (!Files.isDirectory(directoryPath)) {
                return;
            }

            WatchService watchService = FileSystems.getDefault().newWatchService();

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
                        System.out.println("inside event if statement");
                        if (fileName.matches(filePattern)) {
                            System.out.println(fileName);
                            processFile(filePath);
                        }
                        
                    }else{
                        System.out.println("else");
                        processFile(filePath);
                    }
                    
                }
                if (!key.reset()) break; // Reset the key 
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // private List<Product> processFile(Path filePath) {
    //     List<Product> products = new ArrayList<>();
    //     try (BufferedReader reader = Files.newBufferedReader(filePath)){
    //         String line;
    //         while ((line = reader.readLine()) != null) {
    //             String[] columns = line.split(",");
    //             String id = columns.length > 0 ? columns[0] : "";
    //             String brand = columns.length > 1 ? columns[1] : "";
    //             String color = columns.length > 2 ? columns[2] : "";
    //             String size = columns.length > 3 ? columns[3] : "";
    //             String type = columns.length > 4 ? columns[4] : "";
    //             double price = columns.length > 5 ? Double.parseDouble(columns[5]) : 0.0;
    
    //             Product product = price > 0 ? new Product(brand, color, size, price, type) : new Product(brand, color, size, type);
    //             products.add(product);
    //             System.out.println("Loaded products: " + product);  // Debug statement
    //         }
    //     } catch (IOException ex) {
    //         System.err.println("Error reading file: " + filePath + " - " + ex.getMessage());
    //     }
    //     return products;
    // }
    
    

   /**
     * Processes a specific file by reading its content and storing it in the products map.
     * @param filePath the path of the file to process
     * @param brand    the brand associated with the file
     */
    private void processFile(Path filePath) {
        System.out.println(" file: " + filePath);
    
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length < 5) {
                    System.err.println("Invalid line format in file: " + filePath + " - " + line);
                    continue;
                }
    
                String id = columns[0];
                String brand = columns[1];
                String color = columns.length > 2 ? columns[2] : "";
                String size = columns.length > 3 ? columns[3] : "";
                String type = columns.length > 4 ? columns[4] : "";
                double price = columns.length > 5 ? Double.parseDouble(columns[5]) : 0.0;
    
                Product product = price > 0 
                    ? new Product(brand, color, size, price, type) 
                    : new Product(brand, color, size, type);
    
                // Use CompositeKey to store in productMap
                CompositeKey key = new CompositeKey(id, brand);
                productMap.putIfAbsent(key, product);
    
                System.out.println("Product Map size: " + productMap.size());
                System.out.println("Added product: " + product);
            }
        } catch (IOException ex) {
            System.err.println("Error reading file: " + filePath + " - " + ex.getMessage());
        }
    }
    


    public ConcurrentMap<CompositeKey, Product> getProductMap() {
        System.out.println("here: "+productMap.size());
        return productMap;
    }
    
}

    


