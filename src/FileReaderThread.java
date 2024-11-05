
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

// Consumer that reads the files from the shared queue
public class FileReaderThread implements Runnable {
    private final BlockingQueue<String> fileQueue;
    private final BlockingQueue<String> commandQueue; // For user commands
    private final FilterManager filterManager;

    public FileReaderThread(BlockingQueue<String> fileQueue, BlockingQueue<String> commandQueue, FilterManager filterManager) {
        this.fileQueue = fileQueue;
        this.commandQueue = commandQueue;
        this.filterManager = filterManager;
    }

    @Override
    public void run() {
        System.out.println("FileReaderThread is running");
        Thread.currentThread().setName("FileReaderThread");
        System.out.println(Thread.currentThread().getPriority());
        System.out.println(!fileQueue.isEmpty());
        System.out.println(fileQueue.size());
        try {
            while (true) {
            
                try {
                    // First, check for a file in the fileQueue
                    

                    if (!fileQueue.isEmpty()) {
                        String filePath = fileQueue.take();
                        // System.out.println("File found: " + filePath);
                        System.out.println(Thread.currentThread().getName() + " is reading the file: " + filePath);
                        List<Product> products = readProductsFromFile(filePath); // Load products from the file
                        System.out.println("Loaded product: " + products);
    
                        // Process commands only if products are loaded
                        if (!products.isEmpty()) {
                            processCommandsForProducts(products);
                        } else {
                            System.out.println("No products to process after loading.");
                        }
                        
                        
                    }
    
                    Thread.sleep(500); // Short sleep to avoid busy waiting
    
                } catch (InterruptedException e) {
                    System.out.println("FileReaderThread interrupted.");
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        } catch (Exception e) {
            // Handle the exception
            System.out.println("FileReaderThread exception: " + e.getMessage());
            
        }
        
       
    }


    private void processCommandsForProducts(List<Product> products) throws InterruptedException {
        if (commandQueue != null) {   
            System.out.println("Processing commands for products. Number of products: " + products.size());
            if (products.isEmpty()) {
                System.out.println("No products to process.");
                return; // Early exit if there are no products
            }
                    
            String command = commandQueue.take();
            applyCommandAsFilter(command);
            System.out.println("checking products here: " + products);
            List<Product> filteredProducts = filterManager.applyFilters(products);
            // Display filtered results
            System.out.println("Filtered Products based on command: " + command);
            
            System.out.println("Filtered products: " + filteredProducts); // Debug statement
        
            filteredProducts.forEach(System.out::println);
            System.out.println("Filter applied: " + command);
        
        
            System.out.println("Filtered Products based on command: " + command);
            if (!filteredProducts.isEmpty()) {
                filteredProducts.forEach(product -> System.out.println(product));
            } else {
                System.out.println("No products matched the filter: " + command);
            }
            System.out.println("Filter applied: " + command);
        
        
        }
    }


    private List<Product> readProductsFromFile(String filePath) {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                String id = columns.length > 0 ? columns[0] : "";
                String brand = columns.length > 1 ? columns[1] : "";
                String color = columns.length > 2 ? columns[2] : "";
                String size = columns.length > 3 ? columns[3] : "";
                String type = columns.length > 4 ? columns[4] : "";
                double price = columns.length > 5 ? Double.parseDouble(columns[5]) : 0.0;
    
                Product product = price > 0 ? new Product(brand, color, size, price, type) : new Product(brand, color, size, type);
                products.add(product);
                System.out.println("Loaded products: " + product);  // Debug statement
            }
        } catch (IOException ex) {
            System.err.println("Error reading file: " + filePath + " - " + ex.getMessage());
        }
        return products;
    }
    

    private void applyCommandAsFilter(String inputCommand) {
        String[] parts = inputCommand.split("=");
        String attribute = parts[0];
        String value = parts.length > 1 ? parts[1] : "";
        // Debugging statements
        System.out.println("Comparing product color: " + value+ " + color");
    
        // Example of mapping attribute to filter
        switch (attribute) {
            case "color": System.out.println("coloring and clowning"); filterManager.addFilter(new ColorFilter(value)); break;
            case "size": filterManager.addFilter(new SizeFilter(value)); break;
            case "brand": filterManager.addFilter(new BrandFilter(value)); break;
            case "showall": filterManager.clearFilters(); break;
            default: System.out.println("Unknown command: " + inputCommand);
        }
    }
    
}
