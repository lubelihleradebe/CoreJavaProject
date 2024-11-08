package com.nagarro.training.corejavatraining.ConcreteStrategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import com.nagarro.training.corejavatraining.CompositeKey;
import com.nagarro.training.corejavatraining.Product;
import com.nagarro.training.corejavatraining.interfaces.FilterStrategy;

public class ConcreteFilterStrategy implements FilterStrategy{

    @Override
    public List<Product> filter(Map<String, String> criteria, ConcurrentMap<CompositeKey, Product> productMap) {
        List<Product> filteredProducts = new ArrayList<>();

        // Loop through all products in the map
        for (Product product : productMap.values()) {
            boolean isMatch = true;

            // Loop through each criteria in the map
            for (Map.Entry<String, String> entry : criteria.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                // Compare the product's attributes with the criteria
                switch (key) {
                    case "color":
                        if (!product.getColor().equalsIgnoreCase(value)) {
                            isMatch = false;
                        }
                        break;
                    case "size":
                        if (!product.getSize().equalsIgnoreCase(value)) {
                            isMatch = false;
                        }
                        break;
                    case "brand":
                        if (!product.getBrand().equalsIgnoreCase(value)) {
                            isMatch = false;
                        }
                        break;
                    default:
                        System.out.println("Invalid criteria.");
                        isMatch = false;
                        break;
                }

                // If any criteria doesn't match, mark the product as non-matching
                if (!isMatch) {
                    break; // Stop checking other criteria for this product
                }
            }

            // If all criteria matched, add the product to the filtered list
            if (isMatch) {
                filteredProducts.add(product);
            }
        }

        // If no products matched, print a message
        if (filteredProducts.isEmpty()) {
            System.out.println("No products found matching the criteria.");
        }

        return filteredProducts;
    }

    

    // @Override
    // public List<Product> filter(Map<String, String> criteria, ConcurrentMap<CompositeKey, Product> productMap) {
    //     List<Product> filteredProducts = new ArrayList<>();

    //     // Iterate over all the products
    //     for(Product product: productMap.values()){
    //         boolean isMatch = true;

    //         // Check if the product matches the criteria
    //         for(Map.Entry<String, String> entry: criteria.entrySet()){
    //             String key = entry.getKey();
    //             String value = entry.getValue();

    //             // Check if the product has the required attribute
    //             switch(key){
    //                 case "color":
    //                     if(!product.getColor().equalsIgnoreCase(value)){
    //                         isMatch = false;
    //                     }
    //                     break;
    //                 case "size":
    //                     if(!product.getSize().equalsIgnoreCase(value)){
    //                         isMatch = false;
    //                     }
    //                     break;
    //                 case "brand":
    //                     if(!product.getBrand().equalsIgnoreCase(value)){
    //                         isMatch = false;
    //                     }
    //                     break;
    //                 default:
    //                     System.out.println("Invalid criteria.");
    //                     break;
    //             }
    //         }

    //         // If the product matches the criteria, add it to the list
    //         if(isMatch){
    //             filteredProducts.add(product);
                
                
    //         }else if(!isMatch) {
    //             System.out.println("No products found.");
    //             break;
    //         }
    //     }
    //     System.out.println("Product Map /size: " + productMap.size());

    //     return filteredProducts;
    // }
    
}



